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
import com.eds.ctcb.biz.deal.FundIncrementRadio;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.SavingPlanStatus;
import com.eds.ctcb.constant.TradeType;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.db.SavingPlan;
import com.eds.ctcb.db.User;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.form.deal.IrregularInvestmentForm;
import com.eds.ctcb.util.ActionMsgsUtil;
import com.eds.ctcb.util.DataUtil;

public class IrregularInvestmentAction extends BaseAction {

	public ActionForward preIrregularInvestment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IrregularInvestmentForm irregularInvestmentForm = new IrregularInvestmentForm();
		User user = this.investmentBiz.getUser(request);
		irregularInvestmentForm.setCurrencyId(this.investmentBiz.getFirstCurrencyId(user));
		request.getSession().setAttribute("IrregularInvestmentForm", irregularInvestmentForm);
		return this.investmentBiz.preInvestment(mapping, form, request, response);
	}
	public ActionForward irregularInvestment(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
		
		try {
			IrregularInvestmentForm irregularInvestmentForm = (IrregularInvestmentForm) form;
			ActionMessages errors = new ActionMessages();
			//CHECK THE FORM INPUT
			if(irregularInvestmentForm != null){
				// check whether the inputted fund code correct or not
				int fundIdRadio = irregularInvestmentForm.getFundIdRadio();
				String fundCode3 = irregularInvestmentForm.getFundCode3();
				if (fundIdRadio == FundIdRadio.THREE
						&& !DataUtil.isStrEqual(fundCode3, null)) {
					Fund fund = this.investmentBiz.getFundByCode(fundCode3);
					if (fund == null) {
						errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"deal.input.fund.notExists"));
					}
				}
				// check whether the investmentAmount is lower than the min setInvestmentAmount
				BigDecimal minTradeAmount = new BigDecimal(this.sysManagementBiz.getMinTransAmt());
				BigDecimal investmentAmount = new BigDecimal(irregularInvestmentForm.getMonthlyAmount());
				if(investmentAmount.compareTo(minTradeAmount) < 0){
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.tradeAmount.tooLow",new Object[]{minTradeAmount}));
				}
				if(errors.size() > 0){
					this.saveErrors(request, errors);
					return mapping.getInputForward();
				}
			}

			// execute the irregularInvestment transaction
			SavingPlan savingPlan = irregularInvestmentForm2Entity(request,
					irregularInvestmentForm);
			this.irregularInvestmentBiz.irregularInvestment(savingPlan);
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
	private SavingPlan irregularInvestmentForm2Entity(
			HttpServletRequest request, IrregularInvestmentForm form) {
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
			// 5.get increment rate
			BigDecimal rate = null;
			if (form.getFundIncrementRadio() == FundIncrementRadio.ONE) {
				rate = new BigDecimal(form.getIncrementValue()).multiply(BigDecimal.valueOf(100)).divide(
						new BigDecimal(form.getMonthlyAmount()), 5, BigDecimal.ROUND_HALF_UP);

			} else if (form.getFundIncrementRadio() == FundIncrementRadio.TWO) {
				rate = new BigDecimal(form.getIncrementPercentage());
			}

			savingPlan.setAccount(non_frozenCashAccount);
			savingPlan.setTradeType(TradeType.IRREGULAR_INVESTMENT);
			savingPlan.setCreateTime(createTime);
			savingPlan.setUpdateTime(createTime);
			savingPlan.setFund(fund);
			savingPlan.setCount(new BigDecimal(form.getMonthlyAmount()));
			savingPlan.setCurrency(this.investmentBiz.getCurrencyById(form
					.getCurrencyId()));
			savingPlan.setDay(form.getInvestmentDay());
			savingPlan.setIncomingSet(incomingSet);
			savingPlan.setRate(rate);
			savingPlan.setStatus(SavingPlanStatus.VALID);
		}
		return savingPlan;

	}

}



