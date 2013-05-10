package com.eds.ctcb.biz.system;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.constant.LogType;
import com.eds.ctcb.constant.SysParameter;
import com.eds.ctcb.db.ReportInfo;
import com.eds.ctcb.db.SysParam;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.exception.BizExceptionCode;
import com.eds.ctcb.form.system.SysParamForm;
import com.eds.ctcb.form.system.TopicForm;

public class SystemManagementBizImpl extends BaseBiz implements SystemManagementBiz {
	
	private static LogEx log=new LogEx(SystemManagementBizImpl.class);

	public void createSysParamLog(int type,long userId,String content){
		this.logDao.createLog(type, userId, content);
	}
	
	public Date getSystemDate(){
		
		return this.sysParamDao.getSysdate();
	}
	
	
	public  SysParam sysParamForm2Entity(SysParamForm form){
		SysParam sysParam = null;
		if(form != null){
			sysParam = new SysParam(form.getValue(),form.getName(),form.getRemark());
		}else{
			log.error("the instance of SysParamForm is null!");
		}
		
		return sysParam;
	}

	public void updateOneParameter(SysParamForm sysParamForm,Long loginUserId,String logContent){
		this.sysParamDao.updateSysParam(sysParamForm2Entity(sysParamForm));
		//for logging
		this.createLog(LogType.MODIFY_SYS_PARAM,loginUserId,logContent);
		
	}
	
	public void updateMultiParameter(List sysParamList, Long id, String logContent){
		Iterator it=sysParamList.iterator();
		while(it.hasNext()){
			this.sysParamDao.updateSysParam(sysParamForm2Entity((SysParamForm)it.next()));
		}
		this.createLog(LogType.MODIFY_SYS_PARAM,id,logContent);
	}
	
	public String getInitPwd(){
		SysParam sysParam=this.sysParamDao.findSysParamByName(SysParameter.INIT_PASSWORD);
		if(sysParam!=null)
			return sysParam.getValue();
		return "";
	}
	
	public String getInitInvAmount(){
		SysParam sysParam=this.sysParamDao.findSysParamByName(SysParameter.INIT_INV_AMOUNT);
		if(sysParam!=null)
			return sysParam.getValue();
		return "";
	}
	
	public String getLowestHandleTariff(){
		SysParam sysParam=this.sysParamDao.findSysParamByName(SysParameter.LOWEST_HANDLE_TARIFF);
		if(sysParam!=null){
			return sysParam.getValue();
		}
		return "";
	}
	
	public String getHighestHandleTariff(){
		SysParam sysParam=this.sysParamDao.findSysParamByName(SysParameter.HIGHEST_HANDLE_TARIFF);
		if(sysParam!=null)
			return sysParam.getValue();
		return "";
	}
	
	public String getHandleRate(){
		SysParam sysParam=this.sysParamDao.findSysParamByName(SysParameter.HANDLE_RATE);
		if(sysParam!=null)
			return sysParam.getValue();
		return "";
	}
	
	public String getHighestTransferTariff(){
		SysParam sysParam=this.sysParamDao.findSysParamByName(SysParameter.HIGHEST_TRANSFER_TARIFF);
		if(sysParam!=null)
			return sysParam.getValue();
		return "";
	}
	
	public String getLowestTransferTariff(){
		SysParam sysParam=this.sysParamDao.findSysParamByName(SysParameter.LOWEST_TRANSFER_TARIFF);
		if(sysParam!=null)
			return sysParam.getValue();
		return "";
	}
	
	public String getTransferRate(){
		SysParam sysParam=this.sysParamDao.findSysParamByName(SysParameter.TRANSFER_RATE);
		if(sysParam!=null)
			return sysParam.getValue();
		return "";
	}
	
	public String getMinTransAmt(){
		SysParam sysParam=this.sysParamDao.findSysParamByName(SysParameter.MIN_TRANS_AMT);
		if(sysParam!=null)
			return sysParam.getValue();
		return "";
	}
	
	public String getMinFundUnit(){
		SysParam sysParam=this.sysParamDao.findSysParamByName(SysParameter.MIN_FUND_UNIT);
		if(sysParam!=null)
			return sysParam.getValue();
		return "";
	}
	
	
	
	public  Long createReportInfo(TopicForm form,Long loginUserId,String logContent){
	//while existing topic(reportInfo) in specific year and quater,such topic will be updated;
	//	otherwise a new topic(reportInfo) will be created
		Long infoId;
		ReportInfo info = topicForm2Entity(form);
		if(this.reportInfoDao.isAnyDuplicateInfoExist(form.getYear(), form.getQuarter())){
			ReportInfo repInfo=this.reportInfoDao.getReportInfoByFullCondition(form.getYear(), form.getQuarter());
			repInfo.setTopic(form.getTopic());
			this.reportInfoDao.update(repInfo);
			infoId=repInfo.getId();
			//throw (new BizException(BizExceptionCode.SAME_COMPETITION_TOPIC_EXIST,"sysparm.error.duplicatetopic",null,null));
		}
		else
			infoId = this.reportInfoDao.createReportInfo(info);
		this.createLog(LogType.MODIFY_SYS_PARAM,loginUserId,logContent);
		return infoId;
	}

	private ReportInfo topicForm2Entity(TopicForm form) {
		
		ReportInfo info=new ReportInfo(form.getYear(),form.getQuarter(),form.getTopic(),"This is a item of report info");
		return info;
	}
	
	 public List<ReportInfo> getReportListByYear(int year) {
		 List riList=this.reportInfoDao.getReportListByYear(year);
		 if(riList==null) return null;
		return this.reportInfoDao.getReportListByYear(year);

	}
	 
	 public ReportInfo getReportInfoByBothYearAndQuarter(int year, int quarter){
		 return this.reportInfoDao.getReportInfoByFullCondition(new Integer(year), new Integer(quarter));
		 
	 }
	
}
