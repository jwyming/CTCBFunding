package com.eds.ctcb.dao.account;

import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.FundArea;

public class FundAreaDaoImpl extends BaseDaoImpl implements FundAreaDao {
	public List<FundArea> getFundArea(){
		String hql=new String("from FundArea as t");
		QryBean qryBean=new QryBean(hql,null);
		
		return this.qryInList(qryBean);
	}
}
