package com.eds.ctcb.dao.report;

import java.util.List;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.ReportInfo;

public interface ReportInfoDao  extends BaseDao{
	 public abstract Long createReportInfo(ReportInfo info);
	public ReportInfo getReportInfoByFullCondition(Integer year,Integer quarter);
	public ReportInfo getLsatReportInfoByFullCondition(int year, int quarter);
	public  boolean isAnyDuplicateInfoExist(Integer year,Integer quarter);
	public List<ReportInfo> getReportListOrderByTime();
	 public List<ReportInfo> getReportListByYear(int year) ;

}
