package com.eds.ctcb.dao.report;

import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.ReportInfo;
import com.eds.ctcb.db.Role;


public class ReportInfoDaoImpl extends BaseDaoImpl implements ReportInfoDao {
	
	 public List<ReportInfo> getReportListOrderByTime() {
		    String hql="from ReportInfo as t order by t.year,t.quarter desc";
		    QryBean qryBean=new QryBean(hql,null);
		    List list=this.qryInList(qryBean);
		    if(list==null){
		    	return null;
		    }else{
		    	return list;
		    }		   
	}
	 
	 public List<ReportInfo> getReportListByYear(int year) {
		    String hql="from ReportInfo as t  where t.year=? order by t.quarter asc";
		    QryBean qryBean=new QryBean(hql,new Object[]{new Integer(year)});
		    List list=this.qryInList(qryBean);
		    if(list==null){
		    	return null;
		    }else{
		    	return list;
		    }		   
	}

	public Long createReportInfo(ReportInfo info) {
	       this.create(info);
	       return info.getId();
	    }

	public ReportInfo getLsatReportInfoByFullCondition(int year, int quarter) {
		if(quarter==1){
			year=year-1;
			quarter=4;
		}else{
			quarter=quarter-1;
		}
		
		return this.getReportInfoByFullCondition(year, quarter);
		
	}

	public ReportInfo getReportInfoByFullCondition(Integer year, Integer quarter) {
		ReportInfo repInfo=null;
	    String hql="from ReportInfo as t where t.year=? and t.quarter=?";
	    QryBean qryBean=new QryBean(hql,new Object[]{year,quarter});
	    //System.out.println("year:"+year+"------quarter:"+quarter);
	    List list=this.qryInList(qryBean);
	    
	    List tempList = this.qryInList(qryBean);
		if(tempList!=null && tempList.size()==1 && tempList.get(0) instanceof ReportInfo){
			 repInfo =(ReportInfo)(tempList.get(0));
		}
		return repInfo;
//	    if(list==null){
//	    	return null;
//	    }else{
//	    	return (ReportInfo)list.get(0);
//	    }
		
	}
	
	public  boolean isAnyDuplicateInfoExist(Integer year,Integer quarter){
		
		 String hql="from ReportInfo as t where t.year=? and t.quarter=?";
		    QryBean qryBean=new QryBean(hql,new Object[]{year,quarter});
		    
		    List tempList = this.qryInList(qryBean);
			if(tempList!=null && tempList.size()==1 && tempList.get(0) instanceof ReportInfo){
				return true;
			}
			return false;
	}

}
