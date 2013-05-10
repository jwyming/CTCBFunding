package com.eds.ctcb.biz.task;

import com.eds.ctcb.db.TimerTask;
import java.util.List;

/**
 * 
 * @author gzb5dy
 *
 */
public interface TimerTaskBiz {
	
	/**
	 * Write log for each Timer Task.
	 * @param timerTask
	 * @param content
	 */
	public void createTimerTaskLog(TimerTask timerTask, String content);
	
	/**
	 * Initiate the Timer Task.
	 * The taskType is the given Timer Task type, the time is the current time, the count is 0 and the status is Unexecuted. 
	 * @param taskType
	 */
	public void initTimerTask(Integer taskType);
	
	/**
	 * Initiate the Timer Task.
	 * The taskType is the given Timer Task type, the time is the current time, the count is 0 and the status is Unexecuted. 
	 * @param taskType
	 * @return TimerTask timerTask
	 */
	public TimerTask getInitTimerTask(Integer taskType);
	
    /**
	 * Get the unexecuted Timer Task only if the status of Timer Task is unexecuted or failed.
	 * @param taskType
	 * @return List unExecTimerTasks
	 */
	public List<TimerTask> getUnexecutedTimerTask(Integer taskType);
	
	/**
	 * Update the status of the unexecuted Timer Tasks into executed
	 * only if call the methods of *ExecBiz successfully.
	 * @param timerTask
	 * @param taskCount
	 */
	public void updateTimerTaskIfTaskFailed(TimerTask timerTask, Integer taskCount);

	/**
	 * Update the status of the unexecuted Timer Tasks into executed
	 * only if call the methods of *ExecBiz failed.
	 * @param timerTask
	 * @param taskCount
	 */
	public void updateTimerTaskIfTaskSuccessful(TimerTask timerTask, Integer taskCount);
	
	/**
	 * Update the status of the unexecuted Timer Tasks into executed
	 * only if call the methods of *ExecBiz successfully.
	 * @param timerTask
	 */
	public void updateTimerTaskIfTaskFailed(TimerTask timerTask);
	
	/**
	 * Update the status of the unexecuted Timer Tasks into executed
	 * only if call the methods of *ExecBiz failed.
	 * @param timerTask
	 */
	public void updateTimerTaskIfTaskSuccessful(TimerTask timerTask);

}
