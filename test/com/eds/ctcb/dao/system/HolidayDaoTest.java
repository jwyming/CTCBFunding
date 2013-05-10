package com.eds.ctcb.dao.system;

import java.util.Date;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.dao.BaseDaoTest;


public class HolidayDaoTest extends BaseDaoTest {

	private HolidayDao holidayDao;
	private BaseDao baseDao;
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		//this.setPopulateProtectedVariables(true); 
		
		holidayDao =  (HolidayDao) this.applicationContext
				.getBean("holidayDao");
		baseDao = (BaseDao) this.applicationContext.getBean("baseDao");
		
	}

	protected void onTearDownInTransaction() throws Exception {
		baseDao = null;
		holidayDao = null;
		super.onTearDownInTransaction();
		
		
	}

	public void testGetHoliday() {
		Integer state_actual = holidayDao.getHoliday(baseDao.getSysdate());
		Integer state_expected = (Integer) jdbcTemplate.queryForObject(
				"select state from t_holiday where trunc(day)=trunc(?)", new Object[] { new Date() }, Integer.class);
		assertEquals(state_expected, state_actual);
	}
	
	public void testCheckDay(){
		boolean checkDay_expected = holidayDao.checkDay();
		assertTrue(checkDay_expected);
		
	}

}
