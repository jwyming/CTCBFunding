package com.eds.ctcb.dao.account;

import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.FundIndustry;

public class FundIndustryDaoImpl extends BaseDaoImpl implements FundIndustryDao {
	public List<FundIndustry> getAllFundIndustry(){
		String hql=new String("from FundIndustry as t");
		QryBean qryBean=new QryBean(hql,null);
		return this.qryInList(qryBean);
	}
}
