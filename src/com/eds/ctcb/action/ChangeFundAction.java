package com.eds.ctcb.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.eds.ctcb.biz.deal.SellTypeRadio;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.CurrencyType;
import com.eds.ctcb.constant.TradeMode;
import com.eds.ctcb.constant.TradeStatus;
import com.eds.ctcb.constant.TradeType;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.db.FundAccount;
import com.eds.ctcb.db.Trade;
import com.eds.ctcb.db.User;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.form.deal.ChangeFundForm;
import com.eds.ctcb.util.ActionMsgsUtil;
import com.eds.ctcb.util.DataUtil;

public class ChangeFundAction extends BaseAction {

	public ActionForward preChangeFund(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ChangeFundForm changeFundForm = new ChangeFundForm();
		User user = this.investmentBiz.getUser(request);
		changeFundForm.setCurrency(this.investmentBiz.getFirstCurrencyId(user));
		request.getSession().setAttribute("ChangeFundForm", changeFundForm);
		return this.investmentBiz.preInvestment(mapping, form, request, response);
	}
	public ActionForward changeFund(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ChangeFundForm changeFundForm = (ChangeFundForm) form;
		Long userId = this.investmentBiz.getUser(request).getId();
		ActionMessages errors = new ActionMessages();
		BigDecimal fundAmount = this.investmentBiz.getNonFrozenFundAmount(changeFundForm.getOutFundCode(), userId);
		try {
		// CHECK THE FORM INPUT
		if(changeFundForm != null){
			//1.check whether the inputted changeAmount  overTop or is lower than the min settedInvestmentAmount
			BigDecimal minTradeUnit = new BigDecimal(this.sysManagementBiz.getMinFundUnit());
			if(changeFundForm.getChangeFundRadio() == SellTypeRadio.ONE){
				if(fundAmount.compareTo(minTradeUnit) < 0){
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.tradeUnit.tooLow",new Object[]{minTradeUnit}));
				}
			}
			if(changeFundForm.getChangeFundRadio() == SellTypeRadio.TWO){
				BigDecimal partChangeAmount = new BigDecimal(changeFundForm.getPartChangeAmount());
				if (partChangeAmount.compareTo(fundAmount) > 0) {
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.partAmount.overTop"));
				}
				if(partChangeAmount.compareTo(minTradeUnit) < 0){
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.tradeUnit.tooLow",new Object[]{minTradeUnit}));
				}
				
			}
			//2.check whether the investmentDate is before the system date
			String sdate = changeFundForm.getInvestmentDate();
			if(!this.investmentBiz.checkDate(sdate)){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.select.date.error"));
			}
			//3. check whether the inputted partSellAmount or sellValue overTop
			BigDecimal fundSumAmount = this.investmentBiz.getNonFrozenFundAmount(changeFundForm.getOutFundCode(), userId);
			if(changeFundForm.getChangeFundRadio() == SellTypeRadio.TWO ){
				BigDecimal partSellAmount = new BigDecimal(changeFundForm.getPartChangeAmount());
				if (partSellAmount.compareTo(fundSumAmount) > 0) {
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.partAmount.overTop"));           
				}
			}
			// RETURN ALL THE ERRORS TO INPUT fORWARD PAGE
			if(errors.size() > 0){
				this.saveErrors(request, errors);
				return mapping.getInputForward();
			}
		}
		// EXECUTE THE CHANGE_FUND TRANSACTION
			Trade changeFund = changeFundForm2Entity(request, changeFundForm);
			this.changeFundBiz.changeFund(changeFund);

			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveSuccessMessage(request,"deal.investment.success", new Object[] {}, jumpUrl);
			return mapping.findForward("success");
		} catch (BizException e) {
			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveErrorMessage(request,"deal.investment.failure", new Object[] {}, jumpUrl);
			return mapping.findForward("failure");
		}
		
	}
	private Trade changeFundForm2Entity(HttpServletRequest request,
			ChangeFundForm changeFundForm) {
		User user = this.investmentBiz.getUser(request);
		Long userId = user.getId();
		Trade changeFund = new Trade();
		
		if (changeFundForm != null) {
			//1.turn the calendar form String to Date
			Date ddate = DataUtil.str2Date(changeFundForm.getInvestmentDate(), false);
			
			//2.get the inFundAccount and outFundAccount 
			FundAccount outFundAccount = new FundAccount();
			FundAccount inFundAccount = null;
			Fund outFund = this.investmentBiz.getFundByCode(changeFundForm.getOutFundCode());
			Fund inFund = this.investmentBiz.getFundByCode(changeFundForm.getInFundCode());
			outFundAccount = this.investmentBiz.getFundAccount(AccountType.NON_FROZEN_FUND, outFund, user);
			inFundAccount = this.investmentBiz.getFundAccount(AccountType.NON_FROZEN_FUND, inFund, user);

			BigDecimal setCount = BigDecimal.ZERO;
			if (changeFundForm.getChangeFundRadio() == SellTypeRadio.ONE) {
				setCount = this.investmentBiz.getNonFrozenFundAmount(changeFundForm.getOutFundCode(), userId);
			} else if (changeFundForm.getChangeFundRadio() == SellTypeRadio.TWO) {
				setCount = new BigDecimal(changeFundForm.getPartChangeAmount());
			} 
			
			// if found no inFundAccount ,then create a new one
			if (inFundAccount == null) {
				inFundAccount = this.investmentBiz.createFundAccount(AccountType.NON_FROZEN_FUND, user, inFund,0f);
	        }

			// CHANGE_FUND_FORM TO TRADE_ENTITY 
			changeFund.setTradeUser(user);
			changeFund.setCurrency(this.investmentBiz.getCurrencyById(changeFundForm.getCurrency()));
			changeFund.setType(TradeType.CHANGE_FUND);
			changeFund.setAccountBySaccountId(outFundAccount.getAccount());
			changeFund.setAccountByDaccountId(inFundAccount.getAccount());
			changeFund.setCount(setCount);
			changeFund.setTradeMode(TradeMode.UNIT);
			changeFund.setIncomingSet(0f);
			changeFund.setCreateTime(this.investmentBiz.getSysdate());
			changeFund.setStatus(TradeStatus.UNEXECUTED);
			changeFund.setSetTime(ddate);			
			changeFund.setTradeTime(null);
			changeFund.setFee(BigDecimal.ZERO);
		}
		return changeFund;
	}
		
}
