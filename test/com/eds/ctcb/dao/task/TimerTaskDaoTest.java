package com.eds.ctcb.dao.task;

import java.util.Date;
import java.util.List;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.dao.BaseDaoTest;
import com.eds.ctcb.db.TimerTask;

public class TimerTaskDaoTest extends BaseDaoTest {
	
	private TimerTaskDao timerTaskDao;

	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		//this.setPopulateProtectedVariables(true); 

		timerTaskDao = (TimerTaskDao) this.applicationContext
				.getBean("timerTaskDao");

	}

	protected void onTearDownInTransaction() throws Exception {
		timerTaskDao = null;
		super.onTearDownInTransaction();

	}
	
	public void testGetUnexecutedTimerTask(){
		Long expected_id = (Long) jdbcTemplate.queryForObject(
				"select id from t_timertask where (status = ? or status = ?) and tasktype = ? and trunc(createtime)=trunc(?)", 
				new Object[] { new Integer(0), new Integer(-1), new Integer(3), new Date() }, Long.class);
		List<TimerTask> unexecutedTimerTasks = timerTaskDao.getUnexecutedTimerTask(3);
		Long actual_id = null;
		for(TimerTask timerTasks: unexecutedTimerTasks){
			actual_id = timerTasks.getId();
		}
		assertEquals(expected_id, actual_id);
		
	}

}
