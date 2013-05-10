package com.eds.ctcb.dao.system;

import java.util.Calendar;
import java.util.Date;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.dao.BaseDaoTest;

public class TradeTimeDaoTest extends BaseDaoTest {
	
	private TradeTimeDao tradeTimeDao;
	private BaseDao baseDao;
	protected void onSetUpInTransaction() throws Exception{
		super.onSetUpInTransaction();
		tradeTimeDao = (TradeTimeDao) this.getApplicationContext().getBean("tradeTimeDao");
		baseDao = (BaseDao) this.applicationContext.getBean("baseDao");
	}
	
	protected void onTearDownInTransaction() throws Exception{
		baseDao = null;
		tradeTimeDao = null;
		super.onTearDownInTransaction();
		
	}
	
	public void testCheckTime(){
		boolean checkTime = false;
		checkTime = tradeTimeDao.checkTime(1, 3l, 2l, 2l, baseDao.getSysdate());
		assertTrue(checkTime);
	}

}
