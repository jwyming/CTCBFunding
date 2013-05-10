package com.eds.ctcb.task;

import java.util.Date;

import com.eds.ctcb.biz.BizFactory;
import com.eds.ctcb.biz.deal.exec.InvestmentExecBiz;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.constant.TradeStatus;
import com.eds.ctcb.constant.TradeTaskStatus;
import com.eds.ctcb.db.Trade;
import com.eds.ctcb.db.TradeTask;

/**
 * 
 * @author gzb5dy
 *
 */
public class AssignTradeRunnable implements Runnable {
	
	private LogEx log = new LogEx(AssignTradeRunnable.class);

	private TradeTask tradeTask;

	public AssignTradeRunnable(TradeTask tradeTask) {
		this.tradeTask = tradeTask;
	}

	public void run() {
		InvestmentExecBiz investmentExecBiz = BizFactory.getInstance()
				.getInvestmentExecBiz();
		try {
			investmentExecBiz.executeTradeTask(tradeTask);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			Trade trade = tradeTask.getTrade();
			//update the trade status to not executed 
            Date now = investmentExecBiz.getTradeDao().getSysdate();
            trade.setStatus(TradeStatus.UNEXECUTED);
            trade.setTradeTime(now);
            investmentExecBiz.getTradeDao().update(trade);

            // update the trade task status to failed
            tradeTask.setCount(tradeTask.getCount() + 1);
            tradeTask.setLastTime(now);
            tradeTask.setStatus(TradeTaskStatus.FAILED);
            tradeTask.setRemark(e.getMessage());
            investmentExecBiz.getTradeTaskDao().update(tradeTask);
		}
	}
}
