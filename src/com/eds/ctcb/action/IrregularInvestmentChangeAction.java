package com.eds.ctcb.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;

import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.biz.deal.FundIncrementRadio;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.common.SessionKey;
import com.eds.ctcb.constant.Global;
import com.eds.ctcb.constant.SavingPlanStatus;
import com.eds.ctcb.db.SavingPlan;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.form.deal.IrregularInvestmentChangeForm;
import com.eds.ctcb.util.ActionMsgsUtil;
import com.eds.ctcb.util.DataUtil;

public class IrregularInvestmentChangeAction extends BaseAction {
	
	private static LogEx log = new LogEx(IrregularInvestmentChangeAction.class);
	public ActionForward getIrregularInvestmentList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
        try {
            LoginBean loginfo = (LoginBean) request.getSession().getAttribute(
                    SessionKey.GLOBAL_LOGIN_INFO);
            Long userId = loginfo.getUserId();

            int page = 1;
            if (request.getParameter("page") != null
                    && !"".equals(request.getParameter("page"))) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            String sort = request.getParameter("sort");
            String order = request.getParameter("dir");
           PageBean irregularInvestmentPageBean = this.investmentChangeBiz.qryIrregularInvestmentInPage(userId,
                    Global.DEFAULT_PAGE_SIZE, page,sort,order);
           irregularInvestmentPageBean.setSortCriterion(sort);
           if(order == null ||order.equals("asc") ){
        	   irregularInvestmentPageBean.setSortDirection(SortOrderEnum.ASCENDING);
           }else{
        	   irregularInvestmentPageBean.setSortDirection(SortOrderEnum.DESCENDING);
           }
           
            request.setAttribute("irregularInvestmentPageBean", irregularInvestmentPageBean);
            request.setAttribute("valid", SavingPlanStatus.VALID);
            request.setAttribute("invalid", SavingPlanStatus.INVALID);
            

			return mapping.findForward("success");
		} catch (BizException e) {
			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveErrorMessage(request,"deal.investment.failure", new Object[] {}, jumpUrl);
			return mapping.findForward("failure");
        }
    }
    
    
    public ActionForward preIrregularInvestmentChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
      
    	try {
			Long id = Long.parseLong(request.getParameter("savingPlanId"));
			SavingPlan savingPlan = this.investmentChangeBiz.findInvestmentById(id);
			IrregularInvestmentChangeForm irregularInvestmentChangeForm = new IrregularInvestmentChangeForm();
			irregularInvestmentChangeForm.setFundName(savingPlan.getFund().getName());
			irregularInvestmentChangeForm.setSavingPlanId(id);
			irregularInvestmentChangeForm.setInvestmentAmount(savingPlan.getCount().toString());
			irregularInvestmentChangeForm.setInvestmentDate(savingPlan.getDay());			

			BigDecimal incrementPercentage = savingPlan.getRate();
			irregularInvestmentChangeForm.setIncrementPercentage(incrementPercentage.toString());
			
			BigDecimal tempIncrementValue = savingPlan.getCount().multiply(savingPlan.getRate()).divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP);
			BigDecimal incrementValue = new BigDecimal(DataUtil.formatBigDecimal(tempIncrementValue));
			irregularInvestmentChangeForm.setIncrementValue(incrementValue.toString());
			request.getSession().setAttribute(SessionKey.FORM_IRREGULAR_CHANGE, irregularInvestmentChangeForm);
			
			return mapping.findForward("success");
		} catch (BizException e) {
			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveErrorMessage(request,"deal.investment.failure", new Object[] {}, jumpUrl);
			return mapping.findForward("failure");
        }
    }
    
    public ActionForward irregularInvestmentChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
       
    	try {
			IrregularInvestmentChangeForm irregularInvestmentChangeForm = (IrregularInvestmentChangeForm) form;
			log.info("To modify irregularInvestment....");
			Long savingPlanId = irregularInvestmentChangeForm.getSavingPlanId();
			SavingPlan savingPlan = this.investmentChangeBiz.findInvestmentById(savingPlanId);
			savingPlan.setCount(new BigDecimal(irregularInvestmentChangeForm.getInvestmentAmount()));
			savingPlan.setDay(irregularInvestmentChangeForm.getInvestmentDate());
			
			BigDecimal rate = BigDecimal.ZERO;
			if(irregularInvestmentChangeForm.getIncrementRadio()== FundIncrementRadio.ONE){
				rate = new BigDecimal(irregularInvestmentChangeForm.getIncrementValue()).multiply(BigDecimal.valueOf(100)).divide(new BigDecimal(irregularInvestmentChangeForm.getInvestmentAmount()), 10, BigDecimal.ROUND_HALF_UP);

			}else if (irregularInvestmentChangeForm.getIncrementRadio() == FundIncrementRadio.TWO){
				rate = new BigDecimal(irregularInvestmentChangeForm.getIncrementPercentage());
			}
			savingPlan.setRate(rate);
			this.investmentChangeBiz.investmentChange(savingPlan);
			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveSuccessMessage(request,"deal.investment.success", new Object[] {}, jumpUrl);
			return mapping.findForward("success");
		} catch (BizException e) {
			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveErrorMessage(request,"deal.investment.failure", new Object[] {}, jumpUrl);
			return mapping.findForward("failure");
        }
    }
    
   
    public ActionForward reIrregularInvest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
        
    	try {
			Long id = Long.parseLong(request.getParameter("savingPlanId"));
			SavingPlan savingPlan = this.investmentChangeBiz.findInvestmentById(id);
			log.info("To reInvest irregularInvestment....");
			this.investmentChangeBiz.reInvest(savingPlan);
			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveSuccessMessage(request,"deal.investment.success", new Object[] {}, jumpUrl);
			return mapping.findForward("success");
		} catch (BizException e) {
			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveErrorMessage(request,"deal.investment.failure", new Object[] {}, jumpUrl);
			return mapping.findForward("failure");
	        }
    }
    
    public ActionForward stopIrregularInvest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
        
    	try {
			Long id = Long.parseLong(request.getParameter("savingPlanId"));
			SavingPlan savingPlan = this.investmentChangeBiz.findInvestmentById(id);
			log.info("To reInvest irregularInvestment....");
			this.investmentChangeBiz.stopInvest(savingPlan);
			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveSuccessMessage(request,"deal.investment.success", new Object[] {}, jumpUrl);
			return mapping.findForward("success");
		} catch (BizException e) {
			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveErrorMessage(request,"deal.investment.failure", new Object[] {}, jumpUrl);
			return mapping.findForward("failure");
	        }
    }
	
}
