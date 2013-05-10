package com.eds.ctcb.dao.report;

import java.util.List;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.ReportData;

public interface ReportDataDao  extends BaseDao{
public List<ReportData> getReportDataByReportId(Long reportId);

public ReportData getCustReportDataByReportId(Long reportId, Long userId);

public List<ReportData> getPerformanceKingReportData(Long reportId);
public List<ReportData> getAssetConfigKingKingReportData(Long reportId);
public List<ReportData> getSavingPlanKingReportData(Long reportId);

public PageBean qryReportDataInPageByTopic(Long reportId, int pageSize, int page);


}
