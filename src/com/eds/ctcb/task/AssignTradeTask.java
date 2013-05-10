package com.eds.ctcb.task;

import com.eds.ctcb.biz.BizFactory;
import com.eds.ctcb.biz.deal.exec.InvestmentExecBiz;
import com.eds.ctcb.biz.task.TimerTaskBiz;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.constant.TimerTaskType;
import com.eds.ctcb.db.TimerTask;
import com.eds.ctcb.db.TradeTask;
import com.eds.ctcb.exception.BizException;
import java.util.List;

/**
 * 
 * @author gzb5dy
 * 
 */
public class AssignTradeTask extends BaseTask {
	
	private LogEx log = new LogEx(AssignTradeTask.class);
	
	@Override
	public void process() {
		TimerTaskBiz timerTaskBiz = BizFactory.getInstance().getTimerTaskBiz();
		TimerTask timerTask = timerTaskBiz
				.getInitTimerTask(TimerTaskType.TRADE_TASK);
		InvestmentExecBiz investmentExecBiz = BizFactory.getInstance()
				.getInvestmentExecBiz();
		List<TradeTask> unexecutedTradeTaskList = investmentExecBiz
				.getUnexecutedTasks();
		boolean result = true;
		/*
		 * Execute all the trade tasks. If one of them failed, the
		 * timer task will not be successful.
		 */
		for (TradeTask tradeTask : unexecutedTradeTaskList) {
			try {
				result = result && investmentExecBiz.canExecutedNow(tradeTask);
				if (investmentExecBiz.canExecutedNow(tradeTask)) {
					//Execute each trade tasks.
					this.getTaskExecutor().execute(
							new AssignTradeRunnable(tradeTask));
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		if (result) {
			timerTaskBiz.updateTimerTaskIfTaskSuccessful(timerTask);
		} else {
			timerTaskBiz.updateTimerTaskIfTaskFailed(timerTask);
			timerTaskBiz.createTimerTaskLog(timerTask, "Cannot execute all the Trade Tasks now! One of them failed!");
		}
	}
}
