package com.eds.ctcb.dao.task;

import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.constant.TimerTaskType;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.TimerTask;

/**
 * 
 * @author gzb5dy
 *
 */
public class TimerTaskDaoImpl extends BaseDaoImpl implements TimerTaskDao {

	/**
	 * Initiate the Timer Task.
	 * The taskType is the given Timer Task type, the time is the current time, the taskCount is 0 and the status is Unexecuted.
	 * @param taskType
	 */
	public void initTimerTask(Integer taskType) {
		TimerTask timerTask = new TimerTask(taskType, this.getSysdate(), 0, 0, null);	
		this.create(timerTask);
	}
	
	/**
	 * Initiate the Timer Task.
	 * The taskType is the given Timer Task type, the time is the current time, the taskCount is 0 and the status is Unexecuted.
	 * @param taskType
	 * @return TimerTask timerTask
	 */
	public TimerTask getInitTimerTask(Integer taskType) {
		TimerTask timerTask = new TimerTask(taskType, this.getSysdate(), 0, 0, null);	
		this.create(timerTask);
		return timerTask;
	}

	/**
	 * Get the all the unexecuted Timer Tasks only if the status of Timer Task is unexecuted or failed.
	 * @param taskType
	 * @return List unExecTimerTasks
	 */
	public List<TimerTask> getUnexecutedTimerTask(Integer taskType) {
		String hql = "from TimerTask as tt where tt.taskType = ? and trunc(tt.createTime) = trunc(?)";
		QryBean qryBean = new QryBean(hql, new Object[] {taskType, this.getSysdate()});
		List unExecTimerTasks = this.qryInList(qryBean);
		return unExecTimerTasks;
	}

}
