package com.eds.ctcb.task;

import com.eds.ctcb.biz.BizFactory;
import com.eds.ctcb.biz.deal.exec.SavingPlanExecBiz;
import com.eds.ctcb.biz.task.TimerTaskBiz;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.constant.TimerTaskLimitedTimes;
import com.eds.ctcb.constant.TimerTaskStatus;
import com.eds.ctcb.constant.TimerTaskType;
import com.eds.ctcb.db.SavingPlan;
import com.eds.ctcb.db.TimerTask;
import com.eds.ctcb.exception.BizException;

import java.util.List;

/**
 * 
 * @author gzb5dy
 * 
 */
public class SavingPlanTask extends BaseTask {
	
	private LogEx log = new LogEx(SavingPlanTask.class);

	@Override
	public void process() {
		SavingPlanExecBiz savingPlanExecBiz = BizFactory.getInstance()
				.getSavingPlanExecBiz();
		TimerTaskBiz timerTaskBiz = BizFactory.getInstance().getTimerTaskBiz();
		List unExecTimerTasks = timerTaskBiz
				.getUnexecutedTimerTask(TimerTaskType.SAVING_PLAN_TASK);
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
				List<SavingPlan> savingPlans = savingPlanExecBiz
						.getUnexecutedSavingPlans();
				boolean result = true;
				/*
				 * Execute all the saving plans. If one of them failed, the
				 * timer task will not be successful.
				 */
				for (SavingPlan savingPlan : savingPlans) {
					try {
						result = result
								&& savingPlanExecBiz
										.executeSavingPlan(savingPlan);
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				}
				if (result) {
					timerTaskBiz.updateTimerTaskIfTaskSuccessful(timerTask,
							TimerTaskLimitedTimes.SAVING_PLAN_LIMITED_TIMES);
				} else {
					timerTaskBiz.updateTimerTaskIfTaskFailed(timerTask,
							TimerTaskLimitedTimes.SAVING_PLAN_LIMITED_TIMES);
					timerTaskBiz.createTimerTaskLog(timerTask, "Cannot execute all the Saving Plan now! One of them failed!");
				}
			}
		} else {
			timerTaskBiz.initTimerTask(TimerTaskType.SAVING_PLAN_TASK);
		}
	}
}
