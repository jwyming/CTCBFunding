package com.eds.ctcb.action;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.eds.ctcb.biz.BizFactory;
import com.eds.ctcb.biz.deal.ChangeFundBiz;
import com.eds.ctcb.biz.deal.InvestmentBiz;
import com.eds.ctcb.biz.deal.InvestmentChangeBiz;
import com.eds.ctcb.biz.deal.IrregularInvestmentBiz;
import com.eds.ctcb.biz.deal.RegularInvestmentBiz;
import com.eds.ctcb.biz.deal.SellingBiz;
import com.eds.ctcb.biz.deal.SingleInvestmentBiz;
import com.eds.ctcb.biz.deal.SwitchInvestmentBiz;
import com.eds.ctcb.biz.priv.PrivBiz;
import com.eds.ctcb.biz.report.ReportBiz;
import com.eds.ctcb.biz.system.SystemManagementBiz;
import com.eds.ctcb.biz.system.UserBiz;
import com.eds.ctcb.common.LogEx;

public class BaseAction extends MappingDispatchAction  {
	public static LogEx log = new LogEx(BaseAction.class);
	
	protected PrivBiz privBiz = BizFactory.getInstance().getPrivBiz();
	protected InvestmentBiz investmentBiz = BizFactory.getInstance().getInvestmentBiz();
	protected SingleInvestmentBiz singleInvestmentBiz = BizFactory.getInstance().getSingleInvestmentBiz();
	protected RegularInvestmentBiz regularInvestmentBiz = BizFactory.getInstance().getRegularInvestmentBiz();
	protected IrregularInvestmentBiz irregularInvestmentBiz = BizFactory.getInstance().getIrregularInvestmentBiz();
	protected SellingBiz sellingBiz =  BizFactory.getInstance().getSellingBiz();
	protected ChangeFundBiz changeFundBiz =  BizFactory.getInstance().getChangeFundBiz();
	protected SwitchInvestmentBiz switchInvestmentBiz=BizFactory.getInstance().getSwitchInvestmentBiz();
	protected InvestmentChangeBiz investmentChangeBiz = BizFactory.getInstance().getInvestmentChangeBiz();
	protected ReportBiz reportBiz=BizFactory.getInstance().getReportBiz();
    protected SystemManagementBiz sysManagementBiz=BizFactory.getInstance().getSystemManagementBiz();
    protected UserBiz userBiz=BizFactory.getInstance().getUserBiz();
    public ActionForward execute
	(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	 HttpServletResponse response)
	throws Exception {
    	log.enterAction(mapping.getParameter());
    	Map paramMap = request.getParameterMap();
    	
    	if(paramMap!=null){
    		Iterator i = paramMap.keySet().iterator();
    		while(i.hasNext()){
    			String key = (String)(i.next());
    			String value = request.getParameter(key);
    			log.info("- [request param : "+key+"] = "+value);
    		}
    	}

    	return super.execute(mapping, form, request, response);
    }
}
