package com.eds.ctcb.dao.account;

import com.eds.ctcb.dao.BaseDao;
import java.util.List;
import com.eds.ctcb.db.FundType;

public interface FundTypeDao extends BaseDao {
	public List<FundType> getAllFundType();
}
