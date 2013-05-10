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
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.SavingPlanStatus;
import com.eds.ctcb.constant.TradeType;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.db.SavingPlan;
import com.eds.ctcb.db.User;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.form.deal.RegularInvestmentForm;
import com.eds.ctcb.util.ActionMsgsUtil;
import com.eds.ctcb.util.DataUtil;

public class RegularInvestmentAction extends BaseAction {
	public ActionForward preRegularInvestment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RegularInvestmentForm regularInvestmentForm = new RegularInvestmentForm();
		User user = this.investmentBiz.getUser(request);
		regularInvestmentForm.setCurrencyId(this.investmentBiz.getFirstCurrencyId(user));
		request.getSession().setAttribute("RegularInvestmentForm", regularInvestmentForm);
		return this.investmentBiz.preInvestment(mapping, form, request, response);
	}
	public ActionForward regularInvestment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			RegularInvestmentForm regularInvestmentForm = (RegularInvestmentForm) form;
			ActionMessages errors = new ActionMessages();
			// CHECK THE FORM INPUT
			if (regularInvestmentForm != null) {
				// check whether the input fundCode is existed
				int fundIdRadio = regularInvestmentForm.getFundIdRadio();
				String fundCode3 = regularInvestmentForm.getFundCode3();
				if(!this.investmentBiz.checkFundCodeInput(fundCode3, fundIdRadio)){
					errors.add(ActionErrors.GLOBAL_MESSAGE,new ActionMessage("deal.input.fund.notExists"));
				}
				// check whether the investmentAmount is lower than the min setInvestmentAmount
				BigDecimal minTradeAmount = new BigDecimal(this.sysManagementBiz.getMinTransAmt());
				BigDecimal investmentAmount = new BigDecimal(regularInvestmentForm.getMonthlyAmount());
				if(investmentAmount.compareTo(minTradeAmount) < 0){
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.tradeAmount.tooLow",new Object[]{minTradeAmount}));
				}
				if (errors.size() > 0) {
					this.saveErrors(request, errors);
					return mapping.getInputForward();
				}
			}
			// EXECUTE THE REGULAR_INVESTMENT TRANSACTION
			SavingPlan savingPlan = regularInvestmentForm2Entity(request,
					regularInvestmentForm);
			this.regularInvestmentBiz.regularInvestment(savingPlan);

			String jumpUrl = request.getContextPath()
					+ mapping.findForward("return").getPath();
			ActionMsgsUtil.saveSuccessMessage(request,
					"deal.investment.success", new Object[] {}, jumpUrl);
			return mapping.findForward("success");
		} catch (BizException e) {
			e.printStackTrace();
			String jumpUrl = request.getContextPath()
					+ mapping.findForward("return").getPath();
			ActionMsgsUtil.saveErrorMessage(request,
					"deal.investment.failure", new Object[] {}, jumpUrl);
			return mapping.findForward("failure");
		}
	}
	private SavingPlan regularInvestmentForm2Entity(HttpServletRequest request,
			RegularInvestmentForm form) {
		Account non_frozenCashAccount = new Account();
		User user = this.investmentBiz.getUser(request);
		SavingPlan savingPlan = new SavingPlan();
		if (form != null) {
			// 1.get cash account from form
			Long currencyId = form.getCurrencyId();
			non_frozenCashAccount = this.investmentBiz.getCashAccount(AccountType.NON_FROZEN_CASH, currencyId, user).getAccount();
			// 2.get fund from form
			Fund fund = null;
			if (form.getFundIdRadio() == FundIdRadio.ONE) {
				fund = this.investmentBiz.getFundByCode(form.getFundCode1());
			} else if (form.getFundIdRadio() == FundIdRadio.TWO) {
				fund = this.investmentBiz.getFundByCode(form.getFundCode2());
			} else if (form.getFundIdRadio() == FundIdRadio.THREE) {
				fund = this.investmentBiz.getFundByCode(form.getFundCode3());
			}
			// 3.get create time from database
			Date createTime = this.investmentBiz.getSysdate();
			// 4.get incomingSet from form
			Float incomingSet = 0f;
			if (DataUtil.isEmptyStr(form.getInvestmentWarningRate())) {
				incomingSet = 0f;
			} else {
				incomingSet = Float.parseFloat(form.getInvestmentWarningRate());
			}

			savingPlan.setAccount(non_frozenCashAccount);
			savingPlan.setTradeType(TradeType.REGULAR_INVESTMENT);
			savingPlan.setCreateTime(createTime);
			savingPlan.setUpdateTime(createTime);
			savingPlan.setFund(fund);
			savingPlan.setCount(new BigDecimal(form.getMonthlyAmount()));
			savingPlan.setCurrency(this.investmentBiz.getCurrencyById(form
					.getCurrencyId()));
			savingPlan.setDay(form.getInvestmentDay());
			savingPlan.setIncomingSet(incomingSet);
			savingPlan.setRate(BigDecimal.ZERO);
			savingPlan.setStatus(SavingPlanStatus.VALID);
		}
		return savingPlan;

	}
	
}
