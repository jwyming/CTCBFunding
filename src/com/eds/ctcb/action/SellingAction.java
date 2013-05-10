package com.eds.ctcb.action;

import java.math.BigDecimal;
import java.util.Date;

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
import com.eds.ctcb.constant.TradeMode;
import com.eds.ctcb.constant.TradeStatus;
import com.eds.ctcb.constant.TradeType;
import com.eds.ctcb.db.CashAccount;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.db.FundAccount;
import com.eds.ctcb.db.Trade;
import com.eds.ctcb.db.User;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.form.deal.SellingForm;
import com.eds.ctcb.util.ActionMsgsUtil;
import com.eds.ctcb.util.DataUtil;

public class SellingAction extends BaseAction {

	public ActionForward preSelling(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SellingForm sellingForm = new SellingForm();
		User user = this.investmentBiz.getUser(request);
		sellingForm.setCurrency(this.investmentBiz.getFirstCurrencyId(user));
		request.getSession().setAttribute("SellingForm", sellingForm);
		return this.investmentBiz.preInvestment(mapping, form, request, response);
	}
	public ActionForward selling(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SellingForm sellingForm = (SellingForm) form;
		Long userId = this.investmentBiz.getUser(request).getId();
		ActionMessages errors = new ActionMessages();
		try {
			// CHECK THE FORM INPUT
			if(sellingForm != null){
				// 1. check whether the investmentDate is before the system date
				String sdate = sellingForm.getInvestmentDate();
				if(!this.investmentBiz.checkDate(sdate)){
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.select.date.error"));
				}
		
				// 2. check whether the inputted partSellAmount or sellValue overTop
				BigDecimal fundAmount = this.investmentBiz.getNonFrozenFundAmount(sellingForm.getSellFundCode(), userId);
				BigDecimal fundValue = this.investmentBiz.getNonFrozenFundValue(sellingForm.getSellFundCode(), userId);
				if(sellingForm.getSellTypeRadio() == SellTypeRadio.TWO ){
					BigDecimal partSellAmount = new BigDecimal(sellingForm.getPartSellAmount());
					if (partSellAmount.compareTo(fundAmount) > 0) {
						errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.partAmount.overTop"));           
					}
				}
				if(sellingForm.getSellTypeRadio() == SellTypeRadio.THREE ){
					BigDecimal sellValue = new BigDecimal(sellingForm.getSellValue());
					if(sellValue.compareTo(fundValue)> 0){
						errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.sellValue.overTop"));
					}
				}
				//  return all kinds of errors at the inputForward page
				if(errors.size() > 0){
					this.saveErrors(request, errors);
					return mapping.getInputForward();
				}
			}
		
			// Execute The SingleInvestment Transaction
			Trade selling = sellingForm2Entity(request, sellingForm);
			this.sellingBiz.selling(selling);
			
			String jumpUrl = request.getContextPath()+ mapping.findForward("return").getPath();
			ActionMsgsUtil.saveSuccessMessage(request,"deal.investment.success", new Object[] {}, jumpUrl);
			return mapping.findForward("success");
		} catch (BizException e) {
			String jumpUrl = request.getContextPath()+ mapping.findForward("return").getPath();
			ActionMsgsUtil.saveErrorMessage(request,"deal.investment.failure", new Object[] {}, jumpUrl);
			return mapping.findForward("failure");
		}
	}
	private Trade sellingForm2Entity(HttpServletRequest request,SellingForm form) {
		User user = this.investmentBiz.getUser(request);
		Long userId = user.getId();
		FundAccount fundAccount = new FundAccount();
		CashAccount cashAccount = new CashAccount();		
		Trade selling = new Trade();
		
		// turn form to Entity
		if (form != null) {
			// get the fundAccount according to the selected FundCode
			// get the cashAccount of current user and currency
			Fund fund = this.investmentBiz.getFundByCode(form.getSellFundCode());
			Long currencyId = form.getCurrency();
			fundAccount = this.investmentBiz.getFundAccount(AccountType.NON_FROZEN_FUND, fund, user);
			cashAccount = this.investmentBiz.getCashAccount(AccountType.NON_FROZEN_CASH, currencyId, user);
			
			// turn calendar from String to Date
			Date ddate = DataUtil.str2Date(form.getInvestmentDate(), false);
			
			//FOrm To Entity
			selling.setTradeUser(user);
			selling.setCurrency(this.investmentBiz.getCurrencyById(form.getCurrencyRadio()));
			selling.setType(TradeType.SELLING);
			selling.setAccountBySaccountId(fundAccount.getAccount());
			selling.setAccountByDaccountId(cashAccount.getAccount());

			if (form.getSellTypeRadio() == SellTypeRadio.ONE) {
				selling.setCount(this.investmentBiz.getNonFrozenFundAmount(form.getSellFundCode(), userId));
			} else if (form.getSellTypeRadio() == SellTypeRadio.TWO) {
				selling.setCount(new BigDecimal(form.getPartSellAmount()));
			} else if (form.getSellTypeRadio() == SellTypeRadio.THREE) {
				BigDecimal tradeAmount = new BigDecimal(form.getSellValue());
				BigDecimal exchangeRate = this.investmentBiz.getExchangeRate(this.investmentBiz.getCurrencyById(form.getCurrencyRadio()).getType(),fund.getCurrency().getType());
				BigDecimal fundEntity = this.investmentBiz.getFundEquity(fund.getId());
				selling.setCount(tradeAmount.multiply(exchangeRate).divide(fundEntity, 10, BigDecimal.ROUND_HALF_UP));
			}
			selling.setTradeMode(TradeMode.UNIT);
			selling.setIncomingSet(0f);
			selling.setCreateTime(this.investmentBiz.getSysdate());
			selling.setStatus(TradeStatus.UNEXECUTED);
			selling.setSetTime(ddate);
			selling.setTradeTime(null);
			selling.setFee(BigDecimal.ZERO);
		}else {
			log.error("the sellingForm is null!");
		}

		return selling;
	}
	
}
