package com.eds.ctcb.biz.deal.exec;

import com.eds.ctcb.dao.deal.TradeDao;
import com.eds.ctcb.dao.deal.TradeTaskDao;
import com.eds.ctcb.db.Trade;
import com.eds.ctcb.db.TradeTask;
import com.eds.ctcb.exception.BizException;

import java.util.List;

public interface InvestmentExecBiz {

    public List<TradeTask> getUnexecutedTasks() throws BizException;

    public boolean canExecutedNow(TradeTask operation) throws BizException;

    public boolean executeTradeTask(TradeTask tradeTask) throws BizException;
    
    public TradeTaskDao getTradeTaskDao();
    
    public TradeDao getTradeDao();
}
