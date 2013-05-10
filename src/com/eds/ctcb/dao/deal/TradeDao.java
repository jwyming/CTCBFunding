package com.eds.ctcb.dao.deal;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.Trade;

import java.util.List;


import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.dao.BaseDao;


public interface TradeDao  extends BaseDao{
	public List getNotExecTrade(Long userId);

	public PageBean getNotExcuteTrade(Long userId, int pageSize, int page);
	

	public List<Trade> findCurrentMonthInvestment(Account sourceAccount, Account destinationAccount, Integer tradeType);


}
