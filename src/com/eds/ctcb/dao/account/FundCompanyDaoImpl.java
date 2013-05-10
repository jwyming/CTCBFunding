package com.eds.ctcb.dao.account;

import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.FundCompany;

public class FundCompanyDaoImpl extends BaseDaoImpl implements FundCompanyDao {
	public List<FundCompany> getAllFundCompany() {
		String hql = "from FundCompany";
		QryBean qryBean = new QryBean(hql, new Object[]{});
		return this.qryInList(qryBean);
	}

}
