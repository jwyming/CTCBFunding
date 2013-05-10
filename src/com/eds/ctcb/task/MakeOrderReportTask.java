package com.eds.ctcb.task;

import java.util.List;

import com.eds.ctcb.biz.BizFactory;
import com.eds.ctcb.biz.report.ReportBiz;
import com.eds.ctcb.biz.task.TimerTaskBiz;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.constant.TimerTaskLimitedTimes;
import com.eds.ctcb.constant.TimerTaskStatus;
import com.eds.ctcb.constant.TimerTaskType;
import com.eds.ctcb.db.TimerTask;
import com.eds.ctcb.exception.BizException;

/**
 * 
 * @author gzb5dy
 * 
 */
public class MakeOrderReportTask extends BaseTask {

	private LogEx log = new LogEx(MakeOrderReportTask.class);

	@Override
	public void process() {
		ReportBiz reportBiz = BizFactory.getInstance().getReportBiz();
		TimerTaskBiz timerTaskBiz = BizFactory.getInstance().getTimerTaskBiz();
		List unExecTimerTasks = timerTaskBiz
				.getUnexecutedTimerTask(TimerTaskType.MAKE_ORDER_REPORT_TASK);
		if (unExecTimerTasks != null && unExecTimerTasks.size() == 1
				&& unExecTimerTasks.get(0) instanceof TimerTask) {
			TimerTask timerTask = (TimerTask) unExecTimerTasks.get(0);
			if (timerTask.getStatus().equals(TimerTaskStatus.EXECUTED)) {
				timerTaskBiz.createTimerTaskLog(timerTask, "successful!");
			}
			if (timerTask.getStatus().equals(TimerTaskStatus.FAILED)) {
				timerTaskBiz.createTimerTaskLog(timerTask, "failed!");
			}
			if (timerTask.getStatus().equals(TimerTaskStatus.UNEXECUTED)) {
				try {
					if (reportBiz.makeReportData()) {
						timerTaskBiz
								.updateTimerTaskIfTaskSuccessful(
										timerTask,
										TimerTaskLimitedTimes.MAKE_ORDER_REPORT_LIMITED_TIMES);
					} else {
						timerTaskBiz
								.updateTimerTaskIfTaskFailed(
										timerTask,
										TimerTaskLimitedTimes.MAKE_ORDER_REPORT_LIMITED_TIMES);
						timerTaskBiz.createTimerTaskLog(timerTask,
								"Cannot exectue Make Report now!");
					}

				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
			}
		} else {
			timerTaskBiz.initTimerTask(TimerTaskType.MAKE_ORDER_REPORT_TASK);
		}
	}
}
