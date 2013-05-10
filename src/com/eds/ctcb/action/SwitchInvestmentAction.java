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

import com.eds.ctcb.biz.deal.FundIdRadio;
import com.eds.ctcb.biz.deal.SellTypeRadio;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.TradeMode;
import com.eds.ctcb.constant.TradeStatus;
import com.eds.ctcb.constant.TradeType;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.db.FundAccount;
import com.eds.ctcb.db.Trade;
import com.eds.ctcb.db.User;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.form.deal.SwitchInvestmentForm;
import com.eds.ctcb.util.ActionMsgsUtil;
import com.eds.ctcb.util.DataUtil;

public class SwitchInvestmentAction extends BaseAction {
	public ActionForward preSwitchInvestment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SwitchInvestmentForm switchInvestmentForm = new SwitchInvestmentForm();
		User user = this.investmentBiz.getUser(request);
		switchInvestmentForm.setCurrency(this.investmentBiz.getFirstCurrencyId(user));
		request.getSession().setAttribute("SwitchInvestmentForm", switchInvestmentForm);
		return this.investmentBiz.preInvestment(mapping, form, request, response);
	}
	public ActionForward switchInvestment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SwitchInvestmentForm switchInvestmentForm = (SwitchInvestmentForm) form;
		Long userId = this.investmentBiz.getUser(request).getId();
        ActionMessages errors = new ActionMessages();
        try {
        //  CHECK THE FORM INPUT
        if(switchInvestmentForm != null){
        	//1.check whether the inputted partSellAmount or sellValue overTop 
        	String sellFundCode = switchInvestmentForm.getSellFundCode();
        	BigDecimal fundAmount = this.investmentBiz.getNonFrozenFundAmount(sellFundCode, userId);
        	BigDecimal fundValue = this.investmentBiz.getNonFrozenFundValue(sellFundCode, userId);
        	BigDecimal minTradeUnit = new BigDecimal(this.sysManagementBiz.getMinFundUnit());
        	BigDecimal minTradeAmount = new BigDecimal(this.sysManagementBiz.getMinTransAmt());
        	if(switchInvestmentForm.getSellTypeRadio() == SellTypeRadio.ONE ){
        		if (fundAmount.compareTo(minTradeUnit) < 0) {
        			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.tradeUnit.tooLow",new Object[]{minTradeUnit}));
        		}
        	}
        	if(switchInvestmentForm.getSellTypeRadio() == SellTypeRadio.TWO ){
        		BigDecimal partSellAmount = new BigDecimal(switchInvestmentForm.getPartSellAmount());
        		if (partSellAmount.compareTo(fundAmount) > 0) {
        			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.partAmount.overTop"));  
        		}
        		if(partSellAmount.compareTo(minTradeUnit) < 0){
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.tradeUnit.tooLow",new Object[]{minTradeUnit}));
				}
        	}
        	if(switchInvestmentForm.getSellTypeRadio() == SellTypeRadio.THREE ){
        		BigDecimal sellValue = new BigDecimal(switchInvestmentForm.getSellValue());
        		if(sellValue.compareTo(fundValue)> 0){
        			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.sellValue.overTop"));
        		}
        		if(sellValue.compareTo(minTradeAmount) < 0){
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.tradeUnit.tooLow",new Object[]{minTradeAmount}));
				}
        	}
        	//2.check whether the inputted fund code is existed
        	int fundIdRadio = switchInvestmentForm.getFundIdRadio();
        	String fundCode3 = switchInvestmentForm.getFundCode3();
        	if (!this.investmentBiz.checkFundCodeInput(fundCode3,fundIdRadio)) {
        		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.fund.notExists"));
        	}
        	//3.check whether the investmentDate is before the system date
        	String sdate = switchInvestmentForm.getInvestmentDate();
        	if (!this.investmentBiz.checkDate(sdate)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.select.date.error"));
			}
			// RETURN ALL THE ERRORS TO THE INPUT FORWARD PAGE
			if(errors.size() > 0){
				this.saveErrors(request, errors);
				return mapping.getInputForward();
			}
        }
        
			//  EXECUTE THE SWITCH_INVESTMENT TRANSACTION
			Trade switchInvestment = switchInvestmentForm2Entity(request, switchInvestmentForm);
			this.switchInvestmentBiz.switchInvestment(switchInvestment);
			
			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveSuccessMessage(request,"deal.investment.success", new Object[] {}, jumpUrl);
			return mapping.findForward("success");
		} catch (BizException e) {
			String jumpUrl = request.getContextPath()+ mapping.findForward("return").getPath();
			ActionMsgsUtil.saveErrorMessage(request,"deal.investment.failure", new Object[] {}, jumpUrl);
			return mapping.findForward("failure");
		}
	}
	private Trade switchInvestmentForm2Entity(HttpServletRequest request,
			SwitchInvestmentForm switchInvestmentForm) {
		User user = this.investmentBiz.getUser(request);
		Long userId = user.getId();
		Trade switchInvestment = new Trade();
		FundAccount outFundAccount = new FundAccount();
		FundAccount inFundAccount = null;
		BigDecimal setCount = BigDecimal.ZERO;
		//turn the calendar form String to Date
		Date ddate = DataUtil.str2Date(switchInvestmentForm.getInvestmentDate(), false);
		
		
		//get the inFundAccount and outFundAccount 
		Fund outFund = this.investmentBiz.getFundByCode(switchInvestmentForm.getSellFundCode());
		Fund inFund = null;
        if (switchInvestmentForm.getFundIdRadio() == FundIdRadio.ONE) {
        	inFund = this.investmentBiz.getFundByCode(switchInvestmentForm.getFundCode1());
        } else if (switchInvestmentForm.getFundIdRadio() == FundIdRadio.TWO) {
        	inFund = this.investmentBiz.getFundByCode(switchInvestmentForm.getFundCode2());
        } else if (switchInvestmentForm.getFundIdRadio() == FundIdRadio.THREE) {
        	inFund = this.investmentBiz.getFundByCode(switchInvestmentForm.getFundCode3());
        }
        outFundAccount = this.investmentBiz.getFundAccount(AccountType.NON_FROZEN_FUND, outFund, user);
        inFundAccount = this.investmentBiz.getFundAccount(AccountType.NON_FROZEN_FUND, inFund, user);
        
        //get the switchInvestment count
        BigDecimal outFundEntity = this.investmentBiz.getFundEquity(outFund.getId());
        Integer outCurrencyType = outFund.getCurrency().getType();
        if (switchInvestmentForm.getSellTypeRadio() == SellTypeRadio.ONE) {
			setCount = this.investmentBiz.getNonFrozenFundAmount(switchInvestmentForm.getSellFundCode(), userId);
		} else if (switchInvestmentForm.getSellTypeRadio() == SellTypeRadio.TWO) {
			setCount = new BigDecimal(switchInvestmentForm.getPartSellAmount());
		} else if (switchInvestmentForm.getSellTypeRadio() == SellTypeRadio.THREE) {
			BigDecimal tradeAmount = new BigDecimal(switchInvestmentForm.getSellValue());
			BigDecimal exchangeRate = this.investmentBiz.getExchangeRate(this.investmentBiz.getCurrencyById(switchInvestmentForm.getCurrency()).getType(),outCurrencyType);
			setCount = tradeAmount.multiply(exchangeRate).divide(outFundEntity, 10, BigDecimal.ROUND_HALF_UP);
		}


        // if found no inFundAccount ,then create a new one
    	
		if (inFundAccount == null) {
			inFundAccount = this.investmentBiz.createFundAccount(AccountType.NON_FROZEN_FUND, user, inFund, 0f);
        }

		if (switchInvestmentForm != null) {
			
			switchInvestment.setTradeUser(user);
			switchInvestment.setCurrency(this.investmentBiz.getCurrencyById(switchInvestmentForm.getCurrency()));
			switchInvestment.setType(TradeType.SWITCH_INVESTMENT);
			switchInvestment.setAccountBySaccountId(outFundAccount.getAccount());
			switchInvestment.setAccountByDaccountId(inFundAccount.getAccount());
			switchInvestment.setTradeMode(TradeMode.UNIT);
			switchInvestment.setCount(setCount);
			switchInvestment.setIncomingSet(0f);
			switchInvestment.setCreateTime(this.investmentBiz.getSysdate());
			switchInvestment.setStatus(TradeStatus.UNEXECUTED);
			switchInvestment.setSetTime(ddate);
			switchInvestment.setTradeTime(null);
			switchInvestment.setFee(BigDecimal.ZERO);

		}

		return switchInvestment;
	}
	


}
