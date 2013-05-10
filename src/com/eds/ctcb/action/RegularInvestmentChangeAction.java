package com.eds.ctcb.action;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.properties.SortOrderEnum;

import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.common.SessionKey;
import com.eds.ctcb.constant.Global;
import com.eds.ctcb.constant.SavingPlanStatus;
import com.eds.ctcb.db.SavingPlan;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.form.deal.RegularInvestmentChangeForm;
import com.eds.ctcb.util.ActionMsgsUtil;

public class RegularInvestmentChangeAction extends BaseAction {
	private static LogEx log = new LogEx(RegularInvestmentChangeAction.class);

    public ActionForward getRegularInvestmentList(ActionMapping mapping, ActionForm form,
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
            PageBean regularInvestmentPageBean = this.investmentChangeBiz.qryRegularInvestmentInPage(userId,
                    Global.DEFAULT_PAGE_SIZE, page,sort,order);
            regularInvestmentPageBean.setSortCriterion(sort);
            if(order == null||order.equals("asc"))
            	regularInvestmentPageBean.setSortDirection(SortOrderEnum.ASCENDING);
    		else
    			regularInvestmentPageBean.setSortDirection(SortOrderEnum.DESCENDING);

            request.setAttribute("regularInvestmentPageBean", regularInvestmentPageBean);
            request.setAttribute("valid", SavingPlanStatus.VALID);
            request.setAttribute("invalid", SavingPlanStatus.INVALID);
        } catch(BizException e) {
            e.saveMessage(request, request.getContextPath() + mapping.findForward("return").getPath());
            return mapping.findForward("failure");
        }
        return mapping.findForward("success");
    }
    
    public ActionForward preRegularInvestmentChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
      
    	try {
			Long id = Long.parseLong(request.getParameter("savingPlanId"));
			SavingPlan savingPlan = this.investmentChangeBiz.findInvestmentById(id);
			RegularInvestmentChangeForm regularInvestmentChangeForm = new RegularInvestmentChangeForm();
			regularInvestmentChangeForm.setFundName(savingPlan.getFund().getName());
			regularInvestmentChangeForm.setSavingPlanId(id);
			regularInvestmentChangeForm.setInvestmentAmount(savingPlan.getCount().toString());
			regularInvestmentChangeForm.setInvestmentDate(savingPlan.getDay());
			
			request.getSession().setAttribute(SessionKey.FORM_REGULAR_CHANGE, regularInvestmentChangeForm);			
        
			return mapping.findForward("success");
		} catch (BizException e) {
			String jumpUrl = request.getContextPath() + mapping.findForward("return").getPath();
			ActionMsgsUtil.saveErrorMessage(request,"deal.investment.failure", new Object[] {}, jumpUrl);
			return mapping.findForward("failure");
		}
    }

    public ActionForward regularInvestmentChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
       
    	try {
			RegularInvestmentChangeForm regularInvestmentChangeForm = (RegularInvestmentChangeForm) form;
			log.info("To modify irregularInvestment....");
			Long id = regularInvestmentChangeForm.getSavingPlanId();
			SavingPlan savingPlan = this.investmentChangeBiz.findInvestmentById(id);
			savingPlan.setCount(new BigDecimal(regularInvestmentChangeForm.getInvestmentAmount()));
			savingPlan.setDay(regularInvestmentChangeForm.getInvestmentDate());
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
    
    public ActionForward reRegularInvest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
        
    	try {
			Long id = Long.parseLong(request.getParameter("savingPlanId"));
			SavingPlan savingPlan = this.investmentChangeBiz.findInvestmentById(id);
			log.info("To reInvest regularInvestment....");
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
	
    public ActionForward stopRegularInvest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
        
    	try {
			Long id = Long.parseLong(request.getParameter("savingPlanId"));
			SavingPlan savingPlan = this.investmentChangeBiz.findInvestmentById(id);
			log.info("To reInvest regularInvestment....");
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
