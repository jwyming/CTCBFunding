package com.eds.ctcb.biz.task;

import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.constant.TimerTaskStatus;
import com.eds.ctcb.db.TimerTask;
import java.util.List;

/**
 * 
 * @author gzb5dy
 * 
 */
public class TimerTaskBizImpl extends BaseBiz implements TimerTaskBiz {

	/**
	 * Write log for each Timer Task.
	 * @param timerTask
	 * @param content
	 */
	public void createTimerTaskLog(TimerTask timerTask, String content) {
		timerTask.setContent(content);
		this.timerTaskDao.update(timerTask);
	}
	
	/**
	 * Initiate the Timer Task. The Type is the given Timer Task type, the time
	 * is the current time, the count is 0 and the status is Unexecuted.
	 * @param taskType
	 */
	public void initTimerTask(Integer taskType) {
		this.timerTaskDao.initTimerTask(taskType);
	}
	
	/**
	 * Initiate the Timer Task. The Type is the given Timer Task type, the time
	 * is the current time, the count is 0 and the status is Unexecuted.
     * @param taskType
     * @return TimerTask timerTask
	 */
	public TimerTask getInitTimerTask(Integer taskType) {
		return this.timerTaskDao.getInitTimerTask(taskType);
	}

	/**
	 * Get the unexecuted Timer Task only if the status of Timer Task is
	 * unexecuted or failed.
	 * @param taskType
	 * @return List unExecTimerTasks
	 */
	public List<TimerTask> getUnexecutedTimerTask(Integer taskType) {
		return this.timerTaskDao.getUnexecutedTimerTask(taskType);
	}

	/**
	 * Update the status of the unexecuted Timer Tasks into executed only if
	 * call the methods of *ExecBiz failed.
	 * @param timerTask
	 * @param taskCount
	 */
	public void updateTimerTaskIfTaskFailed(TimerTask timerTask,
			Integer taskCount) {
		if (timerTask.getTaskCount() < taskCount) {
			timerTask.setCreateTime(this.timerTaskDao.getSysdate());
			timerTask.setTaskCount(timerTask.getTaskCount() + 1);
			timerTask.setStatus(TimerTaskStatus.UNEXECUTED);
			this.timerTaskDao.update(timerTask);
		} else {
			timerTask.setCreateTime(this.timerTaskDao.getSysdate());
			timerTask.setTaskCount(timerTask.getTaskCount() + 1);
			timerTask.setStatus(TimerTaskStatus.FAILED);
			this.timerTaskDao.update(timerTask);
		}
	}

	/**
	 * Update the status of the unexecuted Timer Tasks into executed only if
	 * call the methods of *ExecBiz successfully.
	 * @param timerTask
	 * @param taskCount
	 */
	public void updateTimerTaskIfTaskSuccessful(TimerTask timerTask,
			Integer taskCount) {
		timerTask.setCreateTime(this.timerTaskDao.getSysdate());
		timerTask.setTaskCount(timerTask.getTaskCount() + 1);
		timerTask.setStatus(TimerTaskStatus.EXECUTED);
		this.timerTaskDao.update(timerTask);
	}

	/**
	 * Update the status of the unexecuted Timer Tasks into executed only if
	 * call the methods of *ExecBiz failed.
	 * @param timerTask
	 */
	public void updateTimerTaskIfTaskFailed(TimerTask timerTask) {
		timerTask.setCreateTime(this.timerTaskDao.getSysdate());
		timerTask.setTaskCount(timerTask.getTaskCount() + 1);
		timerTask.setStatus(TimerTaskStatus.FAILED);
		this.timerTaskDao.update(timerTask);
	}

	/**
	 * Update the status of the unexecuted Timer Tasks into executed only if
	 * call the methods of *ExecBiz successfully.
	 * @param timerTask
	 */
	public void updateTimerTaskIfTaskSuccessful(TimerTask timerTask) {
		timerTask.setCreateTime(this.timerTaskDao.getSysdate());
		timerTask.setTaskCount(timerTask.getTaskCount() + 1);
		timerTask.setStatus(TimerTaskStatus.EXECUTED);
		this.timerTaskDao.update(timerTask);
	}

	
}
