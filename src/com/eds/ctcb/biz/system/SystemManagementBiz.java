package com.eds.ctcb.biz.system;

import java.util.Date;
import java.util.List;

import com.eds.ctcb.constant.SysParameter;
import com.eds.ctcb.db.ReportInfo;
import com.eds.ctcb.db.SysParam;
import com.eds.ctcb.exception.BizException;

import com.eds.ctcb.form.system.SysParamForm;
import com.eds.ctcb.form.system.TopicForm;

public interface SystemManagementBiz {
	
	public Date getSystemDate();
	public void createSysParamLog(int type,long userId,String content);
	public void updateOneParameter(SysParamForm sysParamForm,Long loginUserId,String logContent);
	public String getInitPwd();
	public String getInitInvAmount();
	
	public String getLowestHandleTariff();
	
	public String getHighestHandleTariff();
	
	public String getHandleRate();
	
	public String getHighestTransferTariff();
	
	public String getLowestTransferTariff();
	
	public String getTransferRate();
	
	public String getMinTransAmt();
	
	public String getMinFundUnit();
	
	public  Long createReportInfo(TopicForm form,Long loginUserId,String logContent);
	
	 public List<ReportInfo> getReportListByYear(int year);
	 
	 public ReportInfo getReportInfoByBothYearAndQuarter(int year, int quarter);
	public void updateMultiParameter(List sysParamList, Long id, String logContent);
}
