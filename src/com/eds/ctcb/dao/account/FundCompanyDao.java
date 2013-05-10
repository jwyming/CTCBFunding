package com.eds.ctcb.dao.account;

import java.util.List;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.FundCompany;

public interface FundCompanyDao extends BaseDao {
	public List<FundCompany> getAllFundCompany();
}
