package com.eds.ctcb.dao.account;

import java.math.BigDecimal;



import com.eds.ctcb.dao.BaseDaoTest;


public class FundAccountDaoTest extends BaseDaoTest {
	private FundAccountDao fundAccountDao;
	//private BaseDao baseDao;
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		//this.setPopulateProtectedVariables(true); 
		
		fundAccountDao =  (FundAccountDao) this.applicationContext
				.getBean("fundAccountDao");
		//baseDao = (BaseDao) this.applicationContext.getBean("baseDao");
		
	}

	protected void onTearDownInTransaction() throws Exception {
		//baseDao = null;
		fundAccountDao = null;
		super.onTearDownInTransaction();
		
		
	}

	public void testGetNonFrozenFundAmount() {
		BigDecimal count_actual = fundAccountDao.getNonFrozenFundAmount("2222", 1L);
		BigDecimal count_expected = (BigDecimal) jdbcTemplate.queryForObject(
				"select t_fundAccount.count from t_fundAccount ,t_fund,t_account where t_fund.Id =  t_fundAccount.FUNDID and  t_account.Id = t_fundAccount.ACCOUNTID and  t_account.USERID = ? and  t_fund.CODE = ?"
				, new Object[] { 1L,"2222" }, BigDecimal.class);
		assertEquals(count_expected, count_actual);
	}
	
}
