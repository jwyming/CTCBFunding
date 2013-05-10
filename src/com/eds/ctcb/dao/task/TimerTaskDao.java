package com.eds.ctcb.dao.task;

import java.util.List;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.TimerTask;

/**
 * 
 * @author gzb5dy
 *
 */
public interface TimerTaskDao extends BaseDao {

	/**
	 * Initiate the Timer Task.
     * The Type is the given Timer Task type, the time is the current time, the count is 0 and the status is Unexecuted.
	 * @param taskType
	 */
	public void initTimerTask(Integer taskType);
	
	/**
	 * Initiate the Timer Task.
     * The Type is the given Timer Task type, the time is the current time, the count is 0 and the status is Unexecuted.
     * @param taskType
     * @return TimerTask timerTask
	 */
	public TimerTask getInitTimerTask(Integer taskType);

	/**
	 * Get the unexecuted Timer Tasks only if the status of Timer Task is unexecuted or failed.
	 * @param taskType
	 * @return List unExecTimerTasks
	 */
	public List<TimerTask> getUnexecutedTimerTask(Integer taskType);

}
