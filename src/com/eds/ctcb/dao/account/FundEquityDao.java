package com.eds.ctcb.dao.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.eds.ctcb.dao.BaseDao;

public interface FundEquityDao  extends BaseDao{
	public BigDecimal getFundEquity(Long inFundId, Date inDate);
	public List<Date> getAllEquityDate();
	public List getFundEquityTodayAndOld(Long inFundId,int timeRank);
	public BigDecimal getLatestEqutiy(Long inFundId);
}
