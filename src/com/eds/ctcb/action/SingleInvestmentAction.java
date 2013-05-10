package com.eds.ctcb.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.eds.ctcb.biz.deal.FundIdRadio;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.TradeMode;
import com.eds.ctcb.constant.TradeStatus;
import com.eds.ctcb.constant.TradeType;
import com.eds.ctcb.db.CashAccount;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.db.FundAccount;
import com.eds.ctcb.db.Trade;
import com.eds.ctcb.db.User;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.form.deal.SingleInvestmentForm;
import com.eds.ctcb.util.ActionMsgsUtil;
import com.eds.ctcb.util.DataUtil;

public class SingleInvestmentAction extends BaseAction {

	public ActionForward preSingleInvestment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SingleInvestmentForm singleInvestmentForm = new SingleInvestmentForm();
		User user = this.investmentBiz.getUser(request);
		singleInvestmentForm.setCurrency(this.investmentBiz.getFirstCurrencyId(user));
		request.getSession().setAttribute("SingleInvestmentForm", singleInvestmentForm);
		return this.investmentBiz.preInvestment(mapping, form, request, response);
	}
	public ActionForward singleInvestment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		SingleInvestmentForm singleInvestmentForm = (SingleInvestmentForm) form;
		User user = this.investmentBiz.getUser(request);
		ActionMessages errors = new ActionMessages();
		try {
			//  CHECK THE FORM INPUT
			if(singleInvestmentForm != null){
			//1.check whether the inputted fund code correct or not
				int fundIdRadio = singleInvestmentForm.getFundIdRadio();
				String fundCode3 = singleInvestmentForm.getFundCode3();
				if(!this.investmentBiz.checkFundCodeInput(fundCode3, fundIdRadio)){
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.fund.notExists"));
				}
			//2.check whether the investmentAmount is more than the count of cashAccount 
				BigDecimal fee = BigDecimal.ZERO;
				BigDecimal investmentAmount = new BigDecimal(singleInvestmentForm.getTradeAmount());
				BigDecimal tradeAmount = BigDecimal.ZERO;
				fee = this.investmentBiz.getHandlingTariff(investmentAmount);
				tradeAmount = investmentAmount.add(fee);
				
				CashAccount cashAccount = null;
				BigDecimal cashCount = BigDecimal.ZERO;
				Long currencyId = singleInvestmentForm.getCurrency();
				cashAccount = this.investmentBiz.getCashAccount(AccountType.NON_FROZEN_CASH, currencyId, user);
				cashCount = cashAccount.getCount() ;
				
				
				
				if (tradeAmount.compareTo(cashCount) > 0) {
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.tradeAmount.overTop",new Object[]{cashCount,investmentAmount}));
				}
			//3.check whether the investmentAmount is lower than the min setInvestmentAmount
				BigDecimal minTradeAmount = new BigDecimal(this.sysManagementBiz.getMinTransAmt());
				
				if(investmentAmount.compareTo(minTradeAmount) < 0){
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.tradeAmount.tooLow",new Object[]{minTradeAmount}));
				}
			//4.check whether the investmentDate is before the system date
				String sdate = singleInvestmentForm.getInvestmentDate();
				if(!this.investmentBiz.checkDate(sdate)){
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.select.date.error"));
				}
			//  RETURN ERRORS TO INPUT FORWARD PAGE
				if(errors.size() > 0){
					this.saveErrors(request, errors);
					return mapping.getInputForward();
				}
			}
			// EXECUTE THE SINGLE_INVESTMENT TRANSACTION
			Trade singleInvestment = singleInvestmentForm2Entity(request,singleInvestmentForm);
			this.singleInvestmentBiz.singleInvestment(singleInvestment);
			String jumpUrl = request.getContextPath()
					+ mapping.findForward("return").getPath();
			ActionMsgsUtil.saveSuccessMessage(request,
					"deal.investment.success", new Object[] {}, jumpUrl);
			return mapping.findForward("success");
		} catch (BizException e) {
			String jumpUrl = request.getContextPath()
					+ mapping.findForward("return").getPath();
			ActionMsgsUtil.saveErrorMessage(request,
					"deal.investment.failure", new Object[] {}, jumpUrl);
			return mapping.findForward("failure");
		}

	}
	private Trade singleInvestmentForm2Entity(HttpServletRequest request,
			SingleInvestmentForm form) {
		CashAccount cashAccount = new CashAccount();
		FundAccount fundAccount = null;
		User user = this.investmentBiz.getUser(request);
		Trade singleInvestment = new Trade();
		if (form != null) {
			//1.turn investDate from String to Date
			Date ddate = DataUtil.str2Date(form.getInvestmentDate(), false);
			//2.get the handlingTariff 
			BigDecimal handlingTariff = new BigDecimal(0);
			Double tradeAmount = Double.parseDouble(form.getTradeAmount());
			if (!DataUtil.isEmptyStr(tradeAmount.toString())) {
				handlingTariff = this.investmentBiz.getHandlingTariff(new BigDecimal(tradeAmount.toString()));
			}
			//3.get fund from form
			Fund fund = null;
			if (form.getFundIdRadio() == FundIdRadio.ONE) {
				fund = this.investmentBiz.getFundByCode(form.getFundCode1());
			} else if (form.getFundIdRadio() == FundIdRadio.TWO) {
				fund = this.investmentBiz.getFundByCode(form.getFundCode2());
			} else if (form.getFundIdRadio() == FundIdRadio.THREE) {
				fund = this.investmentBiz.getFundByCode(form.getFundCode3());
			}	
			//4.get incomingSet from form
			Float incomingSet = 0f ;
			if(DataUtil.isEmptyStr(form.getInvestmentWarningRate())){
				incomingSet = 0f;
			}else{
				incomingSet = Float.parseFloat(form.getInvestmentWarningRate());
			}
			//5.get frozenCashAccount & non_frozenFundAccount from database
			Long currencyId = form.getCurrency();
			cashAccount = this.investmentBiz.getCashAccount(AccountType.NON_FROZEN_CASH, currencyId, user);
			fundAccount = this.investmentBiz.getFundAccount(AccountType.NON_FROZEN_FUND, fund, user);
			// if found no non_frozenFundAccount ,then create a new one
			if (fundAccount == null) {
				fundAccount = this.investmentBiz.createFundAccount(AccountType.NON_FROZEN_FUND, user, fund,incomingSet);
	        }

			// SINGLE_INVESTMENT_FORM TO TRADE_ENTITY
			singleInvestment.setTradeUser(user);
			singleInvestment.setType(TradeType.SINGLE_INVESTMENT);
			singleInvestment.setAccountBySaccountId(cashAccount.getAccount());
			singleInvestment.setAccountByDaccountId(fundAccount.getAccount());
			singleInvestment.setTradeMode(TradeMode.MONEY);
			singleInvestment.setCount(new BigDecimal(form.getTradeAmount()));
			singleInvestment.setCurrency(this.investmentBiz.getCurrencyById(form.getCurrency()));
			singleInvestment.setSetTime(ddate);
			singleInvestment.setIncomingSet(incomingSet);
			singleInvestment.setCreateTime(this.investmentBiz.getSysdate());
			singleInvestment.setStatus(TradeStatus.UNEXECUTED);
			singleInvestment.setSetTime(ddate);
			singleInvestment.setFee(handlingTariff);
		} else {
			log.error("the singleInvestmentForm is null!");
		}
		return singleInvestment;
	}
	
}
