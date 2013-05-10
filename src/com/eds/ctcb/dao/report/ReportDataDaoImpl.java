package com.eds.ctcb.dao.report;

import java.util.List;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.ReportData;

public class ReportDataDaoImpl extends BaseDaoImpl implements ReportDataDao {

	public PageBean qryReportDataInPageByTopic(Long reportId, int pageSize, int page) {
		String hql="from ReportData as t where t.reportInfo.id=? and t.rank<>0  order by t.rank asc ";
		QryBean qryBean=new QryBean(hql,new Object[]{reportId});
		
		return this.qryInPage(qryBean, pageSize, page);
	}

	public ReportData getCustReportDataByReportId(Long reportId, Long userId) {
		String hql="from ReportData as t where t.reportInfo.id=? and t.user.id=?";
		QryBean qryBean=new QryBean(hql,new Object[]{reportId,userId});
		List <ReportData> result= this.qryInList(qryBean);
		if(result.size()==0){
			return null;
		}else{
			return result.get(0);
		}
	}

	public List<ReportData> getReportDataByReportId(Long reportId) {
		String hql="from ReportData as t where t.reportInfo.id=? and t.rank<>0  order by t.rank asc ";
		QryBean qryBean=new QryBean(hql,new Object[]{reportId});
		return this.qryInList(qryBean);
	}

	public List<ReportData> getPerformanceKingReportData(Long reportId) {
		String hql="from ReportData as t where t.reportInfo.id=? and t.incomingRate>=? order by t.incomingRate desc ";
		QryBean qryBean=new QryBean(hql,new Object[]{reportId,new Float(0.0f)});
		return this.qryInList(qryBean);
	}

	public List<ReportData> getAssetConfigKingKingReportData(Long reportId) {
		
		//SQL:SELECT * FROM T_REPORTDATA WHERE USERID IN (SELECT USERID  FROM T_ACCOUNT  GROUP BY USERID HAVING COUNT(ID)>4);
		String hql="from ReportData as t where ( t.reportInfo.id=? and t.user.id in (select a.user.id from Account as a WHERE a.type<>1 and a.type<>2 group by a.user.id having count(a.user.id)>=?))) order by t.incomingRate desc ";
		QryBean qryBean=new QryBean(hql,new Object[]{reportId,new Integer(4)});
		return this.qryInList(qryBean);
		
	}

	public List<ReportData> getSavingPlanKingReportData(Long reportId) {
		
		String hql="from ReportData as t where t.reportInfo.id=? and t.user.id in (select a.user.id from Account as a,SavingPlan as s WHERE a.id=s.account.id) order by t.incomingRate desc ";
		
		QryBean qryBean=new QryBean(hql,new Object[]{reportId});
		
		return this.qryInList(qryBean);
	}

	

}
