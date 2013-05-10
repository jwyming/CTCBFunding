package com.eds.ctcb.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.common.SessionKey;
import com.eds.ctcb.constant.LogType;
import com.eds.ctcb.constant.RoleType;
import com.eds.ctcb.constant.SysParameter;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.ReportInfo;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.form.system.HandleTariffForm;
import com.eds.ctcb.form.system.InvAmountForm;
import com.eds.ctcb.form.system.MiscSettingForm;
import com.eds.ctcb.form.system.SysParamForm;
import com.eds.ctcb.form.system.TopicForm;
import com.eds.ctcb.form.system.TransferTariffForm;
import com.eds.ctcb.util.ActionMsgsUtil;
import com.eds.ctcb.util.DataUtil;
import com.eds.ctcb.util.I18NUtil;
public class SysParamAction extends BaseAction {
	
	private static LogEx log = new LogEx(SysParamAction.class);
	public ActionForward preSetInitPwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String pwd=this.sysManagementBiz.getInitPwd();
		String pwd1="initial_password";
		if(pwd!=null){
			request.setAttribute(pwd1,pwd);
		}
		return mapping.findForward("success");
	}

	public ActionForward setInitialPwd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//get Login info for logging
		LoginBean loginfo = (LoginBean) request.getSession().getAttribute(
				SessionKey.GLOBAL_LOGIN_INFO);
		Long id=loginfo.getUserId();
		String logContent =loginfo.getUserName()+" set system initial password at "
			+DataUtil.date2Str(this.sysManagementBiz.getSystemDate(), true) ;

		SysParamForm sysParamForm=(SysParamForm)form;
		sysParamForm.setName(SysParameter.INIT_PASSWORD);
		sysParamForm.setRemark("This is the system password");
		try{
			this.sysManagementBiz.updateOneParameter(sysParamForm,id,logContent);
			//this.sysManagementBiz.createSysParamLog(LogType.MODIFY_SYS_PARAM,id,logContent);
			String jumpUrl = request.getContextPath()+mapping.findForward("success").getPath();
			ActionMsgsUtil.saveSuccessMessage(request, "system.createPwdSucess",
					null, jumpUrl);
		}catch(Exception e){
			String jumpUrl = request.getContextPath()+ mapping.findForward("failure").getPath();
			ActionMsgsUtil.saveErrorMessage(request, "system.createPwdFail",
					null, jumpUrl);
			
		}
		return mapping.findForward("showMsg");
	}
	
	public ActionForward preSetInitInvAmount(ActionMapping mapping,
			ActionForm form,HttpServletRequest request,
			HttpServletResponse response) {
		
		String amount=this.sysManagementBiz.getInitInvAmount();
		String inv_amt="init_inv_amount";
		if(inv_amt!=null){
			request.setAttribute(inv_amt,amount);
		}
		return mapping.findForward("success");
		
	}
	
	
	public ActionForward setInitInvAmount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//get Login info for logging
		LoginBean loginfo = (LoginBean) request.getSession().getAttribute(
				SessionKey.GLOBAL_LOGIN_INFO);
		Long id=loginfo.getUserId();
		String logContent =loginfo.getUserName()+" set initial investment amount at "
			+DataUtil.date2Str(this.sysManagementBiz.getSystemDate(), true);
		
		InvAmountForm amountForm=(InvAmountForm)form;
		String amount=amountForm.getAmount();
		SysParamForm sysParamForm=new SysParamForm();
		sysParamForm.setValue(amount);
		sysParamForm.setName(SysParameter.INIT_INV_AMOUNT);
		sysParamForm.setRemark("This is the initial investment amount");
		try{
			this.sysManagementBiz.updateOneParameter(sysParamForm,id,logContent);
			//for logging 
			//this.sysManagementBiz.createSysParamLog(LogType.MODIFY_SYS_PARAM,id,logContent);
			String jumpUrl = request.getContextPath()+mapping.findForward("success").getPath();
			ActionMsgsUtil.saveSuccessMessage(request, "system.createInitInvAmountSucess",
					null, jumpUrl);
		}catch(Exception e){
			String jumpUrl = request.getContextPath()+ mapping.findForward("failure").getPath();
			ActionMsgsUtil.saveErrorMessage(request, "system.createInitInvAmountFail",
					null, jumpUrl);
			
		}
		return mapping.findForward("showMsg");
	}
	
	
	public ActionForward preSetHandleTariff(ActionMapping mapping,
			ActionForm form,HttpServletRequest request,
			HttpServletResponse response) {
		String min=this.sysManagementBiz.getLowestHandleTariff();
		String max=this.sysManagementBiz.getHighestHandleTariff();
		String rate=this.sysManagementBiz.getHandleRate();
		HandleTariffForm handleTariffForm=new HandleTariffForm(min,max,rate);
		request.getSession().setAttribute(SessionKey.FORM_HANDLE_TARIFF, handleTariffForm);
		return mapping.findForward("success");
		
	}
	
	public ActionForward setHandleTariff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//get Login info for logging
		LoginBean loginfo = (LoginBean) request.getSession().getAttribute(
				SessionKey.GLOBAL_LOGIN_INFO);
		Long id=loginfo.getUserId();
		String logContent =loginfo.getUserName()+" set handling tariff at "
			+DataUtil.date2Str(this.sysManagementBiz.getSystemDate(), true);
		
		HandleTariffForm tariffForm=(HandleTariffForm)form;
		String min=tariffForm.getMin();
		String max=tariffForm.getMax();
		String rate=tariffForm.getRate();
		
		String [] value=new String[]{min,max,rate};
		String [] name=new String[]{SysParameter.LOWEST_HANDLE_TARIFF,
				SysParameter.HIGHEST_HANDLE_TARIFF,SysParameter.HANDLE_RATE};
		String [] remark=new String[]{"this is min handling tariff",
			"this is max handling tariff","this is handling rate"};
		ArrayList<SysParamForm> sysParamList=new ArrayList<SysParamForm>();
			
		try{
			
			for(int i=0;i<3;i++){
				sysParamList.add(new SysParamForm(value[i],name[i],remark[i]));
				
			}
			this.sysManagementBiz.updateMultiParameter(sysParamList,id,logContent);
			//for logging
			//this.sysManagementBiz.createSysParamLog(LogType.MODIFY_SYS_PARAM,id,logContent);
			String jumpUrl = request.getContextPath()+mapping.findForward("success").getPath();
			ActionMsgsUtil.saveSuccessMessage(request, "system.createHandleTariffSucess",
					null, jumpUrl);
		
		}catch(Exception e){
			String jumpUrl = request.getContextPath()+ mapping.findForward("failure").getPath();
			ActionMsgsUtil.saveErrorMessage(request, "system.createHandleTariffFail",
					null, jumpUrl);
			
		}
		return mapping.findForward("showMsg");
	}
	
	public ActionForward preSetTransferTariff(ActionMapping mapping,
			ActionForm form,HttpServletRequest request,
			HttpServletResponse response) {
		String min=this.sysManagementBiz.getLowestTransferTariff();
		String max=this.sysManagementBiz.getHighestTransferTariff();
		String rate=this.sysManagementBiz.getTransferRate();
		TransferTariffForm transferTariffForm=new TransferTariffForm(min,max,rate);
		request.getSession().setAttribute(SessionKey.FORM_TRANSFER_TARIFF, transferTariffForm);
		return mapping.findForward("success");
		
	}
	
	public ActionForward setTransferTariff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//get Login Info for Logging
		LoginBean loginfo = (LoginBean) request.getSession().getAttribute(
				SessionKey.GLOBAL_LOGIN_INFO);
		Long id=loginfo.getUserId();
		String logContent =loginfo.getUserName()+" set transfer tariff at "
			+DataUtil.date2Str(this.sysManagementBiz.getSystemDate(), true);
		
		TransferTariffForm transferForm=(TransferTariffForm)form;
		String min=transferForm.getMin();
		String max=transferForm.getMax();
		String rate=transferForm.getRate();
		
		String [] value=new String[]{min,max,rate};
		String [] name=new String[]{SysParameter.LOWEST_TRANSFER_TARIFF,
				SysParameter.HIGHEST_TRANSFER_TARIFF,SysParameter.TRANSFER_RATE};
		String [] remark=new String[]{"this is min transfer tariff",
			"this is max transfer tariff","this is transfer rate"};
		ArrayList<SysParamForm> sysParamList=new ArrayList<SysParamForm>();
		
			
		
		try{

			for(int i=0;i<3;i++){
				sysParamList.add(new SysParamForm(value[i],name[i],remark[i]));
			}
		
			this.sysManagementBiz.updateMultiParameter(sysParamList, id, logContent);
			//for logging
			//this.sysManagementBiz.createSysParamLog(LogType.MODIFY_SYS_PARAM,id,logContent);
			String jumpUrl = request.getContextPath()+mapping.findForward("success").getPath();
			ActionMsgsUtil.saveSuccessMessage(request, "system.createTransferTariffSucess",
					null, jumpUrl);
		
		}catch(Exception e){
			String jumpUrl = request.getContextPath()+ mapping.findForward("failure").getPath();
			ActionMsgsUtil.saveErrorMessage(request, "system.createTransferTariffFail",
					null, jumpUrl);
			
		}
		return mapping.findForward("showMsg");
	}
	
	public ActionForward preSetCompetitionTopic(ActionMapping mapping,
			ActionForm form,HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("success");
		
	}
	
	public ActionForward setCompetitionTopic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
//		get Login Info for Logging
		LoginBean loginfo = (LoginBean) request.getSession().getAttribute(
				SessionKey.GLOBAL_LOGIN_INFO);
		Long id=loginfo.getUserId();
		String logContent =loginfo.getUserName()+" set Competition topic at "
			+DataUtil.date2Str(this.sysManagementBiz.getSystemDate(), true);
		
		
		TopicForm topicForm=(TopicForm)form;
		String topic=topicForm.getTopic();
		Integer year=topicForm.getYear();
		Integer quarter=topicForm.getQuarter();
		if(this.sysManagementBiz.getReportInfoByBothYearAndQuarter(year, quarter)!=null){
			ActionMsgsUtil.saveErrorMessage(request, "sysparm.error.duplicatetopic",
					null, "");
		
			return mapping.getInputForward();
		}
		
		try{
			this.sysManagementBiz.createReportInfo(topicForm,id,logContent);
			
			//for logging
			//this.sysManagementBiz.createSysParamLog(LogType.MODIFY_SYS_PARAM,id,logContent);
		
			String jumpUrl = request.getContextPath()+mapping.findForward("success").getPath();
			ActionMsgsUtil.saveSuccessMessage(request, "system.createCompetitionTopicSucess",
					null, jumpUrl);
			
		
		}catch(BizException e){
			String jumpUrl = request.getContextPath()+ mapping.findForward("failure").getPath();
			ActionMsgsUtil.saveErrorMessage(request, "system.createCompetitionTopicFail",
					null, jumpUrl);
		}
		
		return mapping.findForward("showMsg");
			
	}
	
	
	public ActionForward preSetMisc(ActionMapping mapping,
			ActionForm form,HttpServletRequest request,
			HttpServletResponse response) {
		String amt=this.sysManagementBiz.getMinTransAmt();
		String unit=this.sysManagementBiz.getMinFundUnit();
		MiscSettingForm msForm=new MiscSettingForm(amt,unit);
		request.getSession().setAttribute(SessionKey.FORM_MISC_SETTING, msForm);
		return mapping.findForward("success");	
	}
	
	
	public ActionForward setMisc(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		//get Login Info for Logging
		LoginBean loginfo = (LoginBean) request.getSession().getAttribute(
				SessionKey.GLOBAL_LOGIN_INFO);
		Long id=loginfo.getUserId();
		String logContent =loginfo.getUserName()+
		" set min transaction amount and min fund investment unit at "
			+DataUtil.date2Str(this.sysManagementBiz.getSystemDate(), true);
		
		MiscSettingForm msForm=(MiscSettingForm)form;
		String amt=msForm.getMinAmt();
		String unit=msForm.getMinUnit();
		
		String [] value=new String[]{amt,unit};
		String [] name=new String[]{SysParameter.MIN_TRANS_AMT,
				SysParameter.MIN_FUND_UNIT};
		String [] remark=new String[]{"min transaction amount",
			"this is min fund investment unit"};
		ArrayList<SysParamForm> sysParamList=new ArrayList<SysParamForm>();
 
		try{

			for(int i=0;i<2;i++){
				sysParamList.add(new SysParamForm(value[i],name[i],remark[i]));
			}
			this.sysManagementBiz.updateMultiParameter(sysParamList,id,logContent);
			//for logging
			//this.sysManagementBiz.createSysParamLog(LogType.MODIFY_SYS_PARAM,id,logContent);
			String jumpUrl = request.getContextPath()+mapping.findForward("success").getPath();
			ActionMsgsUtil.saveSuccessMessage(request, "system.createMiscSettingSucess",
					null, jumpUrl);
		
		}catch(Exception e){
			String jumpUrl = request.getContextPath()+ mapping.findForward("failure").getPath();
			ActionMsgsUtil.saveErrorMessage(request, "system.createMiscSettingFail",
					null, jumpUrl);
			
		}
		return mapping.findForward("showMsg");
	}
	
	
	//Ajax selection of Quarter per year which has topic setting 
	public ActionForward getTopicQuarter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		String year = request.getParameter("param");
		if(year.equals("")){
			year="0";
		}
		request.getSession().setAttribute("year", year);
		HashMap quarterMap=new LinkedHashMap();
		
		
		String firstq=I18NUtil.getResourceBundle(request).getString("Options.Quarter.1st");
		String secondq=I18NUtil.getResourceBundle(request).getString("Options.Quarter.2nd");
		String thirdq=I18NUtil.getResourceBundle(request).getString("Options.Quarter.3rd");
		String fourthq=I18NUtil.getResourceBundle(request).getString("Options.Quarter.4th");
		String [] quarter=new String[]{firstq,secondq,thirdq,fourthq};
		for(int i=0;i<quarter.length;i++){
			quarterMap.put(new Integer(i+1), quarter[i]);
		}
	
		request.getSession().setAttribute(SessionKey.QUARTER_MAP, quarterMap);
		
		return mapping.findForward("success");
		//return mapping.getInputForward();
	}
	
	
	public ActionForward getTopic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String quarter = request.getParameter("param");
		if(quarter.equals("")){
			quarter="0";
		}
		String year=(String)request.getSession().getAttribute("year");
		//System.out.println(year+quarter);
		HashMap topicMap=new LinkedHashMap();
		String perfKing=I18NUtil.getResourceBundle(request).getString("competitionTopic.performanceKing");
		String assetConfKing=I18NUtil.getResourceBundle(request).getString("competitionTopic.assetConfigurationKing");
		String savingKing=I18NUtil.getResourceBundle(request).getString("competitionTopic.savingKing");
		
		String [] king=new String[]{perfKing,assetConfKing,savingKing};
		for(int i=0;i<king.length;i++){
			topicMap.put(new Integer(i+1), king[i]);
		}
		
		request.getSession().setAttribute(SessionKey.TOPIC_MAP, topicMap);
		ReportInfo repInfo=	this.sysManagementBiz.getReportInfoByBothYearAndQuarter
			(Integer.parseInt(year), Integer.parseInt(quarter));
		if(repInfo==null){
			request.getSession().setAttribute("id", new Integer(0));
			//System.out.println("aaaa");
		}
		else{
			request.getSession().setAttribute("id", repInfo.getTopic());
			//System.out.println("bbbb");
		}
		
		return mapping.findForward("success");
	}
	
}
