package com.eds.ctcb.dao.account;

import java.util.List;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.FundArea;

public interface FundAreaDao extends BaseDao {

	public List<FundArea> getFundArea();

}
