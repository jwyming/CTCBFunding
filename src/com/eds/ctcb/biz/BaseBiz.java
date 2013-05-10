package com.eds.ctcb.biz;

import java.util.ArrayList;
import java.util.List;

import com.eds.ctcb.dao.account.AccountDao;
import com.eds.ctcb.dao.account.CashAccountDao;
import com.eds.ctcb.dao.account.CurrencyDao;
import com.eds.ctcb.dao.account.ExchangeRateDao;
import com.eds.ctcb.dao.account.FundAccountDao;
import com.eds.ctcb.dao.account.FundAreaDao;
import com.eds.ctcb.dao.account.FundCompanyDao;
import com.eds.ctcb.dao.account.FundDao;
import com.eds.ctcb.dao.account.FundEquityDao;
import com.eds.ctcb.dao.account.FundIndustryDao;
import com.eds.ctcb.dao.account.FundTypeDao;
import com.eds.ctcb.dao.account.UserDao;
import com.eds.ctcb.dao.deal.SavingPlanDao;
import com.eds.ctcb.dao.deal.TradeDao;
import com.eds.ctcb.dao.deal.TradeTaskDao;
import com.eds.ctcb.dao.priv.ResourceDao;
import com.eds.ctcb.dao.priv.ResourceLocationDao;
import com.eds.ctcb.dao.priv.RoleDao;
import com.eds.ctcb.dao.priv.RoleResourceDao;
import com.eds.ctcb.dao.priv.UserRoleDao;
import com.eds.ctcb.dao.report.ReportDataDao;
import com.eds.ctcb.dao.report.ReportInfoDao;
import com.eds.ctcb.dao.system.HolidayDao;
import com.eds.ctcb.dao.system.LogDao;
import com.eds.ctcb.dao.system.SysParamDao;
import com.eds.ctcb.dao.system.TradeTimeDao;
import com.eds.ctcb.dao.task.TimerTaskDao;

public class BaseBiz {
	public void createLog(int type,long userId,String content){
		this.logDao.createLog(type, userId, content);
	}
	

	protected UserDao userDao;

	protected UserRoleDao userRoleDao;

	protected ResourceDao resourceDao;

	protected RoleResourceDao roleResourceDao;

	protected TradeDao tradeDao;

	protected TradeTaskDao tradeTaskDao;

	protected SavingPlanDao savingPlanDao;

	protected SysParamDao sysParamDao;

	protected CashAccountDao cashAccountDao;

	protected FundAccountDao fundAccountDao;

	protected RoleDao roleDao;

	protected ResourceLocationDao resourceLocationDao;
	
	protected FundDao fundDao;

	protected FundTypeDao fundTypeDao;

	protected FundCompanyDao fundCompanyDao;

	protected FundAreaDao fundAreaDao;

	protected FundIndustryDao fundIndustryDao;

	protected CurrencyDao currencyDao;

    protected FundEquityDao fundEquityDao;

  protected ExchangeRateDao exchangeRateDao;
    
    protected LogDao logDao;

  protected TradeTimeDao tradeTimeDao;

  protected HolidayDao holidayDao;

  protected AccountDao accountDao;

    protected TimerTaskDao timerTaskDao;

  protected ReportInfoDao reportInfoDao;
  protected ReportDataDao reportDataDao;

	

	public ReportDataDao getReportDataDao() {
	return reportDataDao;
}

public void setReportDataDao(ReportDataDao reportDataDao) {
	this.reportDataDao = reportDataDao;
}

public ReportInfoDao getReportInfoDao() {
	return reportInfoDao;
}

public void setReportInfoDao(ReportInfoDao reportInfoDao) {
	this.reportInfoDao = reportInfoDao;
}

	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setRoleResourceDao(RoleResourceDao roleResourceDao) {
		this.roleResourceDao = roleResourceDao;
	}

	public void setSavingPlanDao(SavingPlanDao savingPlanDao) {
		this.savingPlanDao = savingPlanDao;
	}

	public void setSysParamDao(SysParamDao sysParamDao) {
		this.sysParamDao = sysParamDao;
	}

	public void setTradeDao(TradeDao tradeDao) {
		this.tradeDao = tradeDao;
	}

	public void setTradeTaskDao(TradeTaskDao tradeTaskDao) {
		this.tradeTaskDao = tradeTaskDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setCashAccountDao(CashAccountDao cashAccountDao) {
		this.cashAccountDao = cashAccountDao;
	}

	public void setFundAccountDao(FundAccountDao fundAccountDao) {
		this.fundAccountDao = fundAccountDao;
	}

	public void setFundDao(FundDao fundDao) {
		this.fundDao = fundDao;
	}

	public void setFundCompanyDao(FundCompanyDao fundCompanyDao) {
		this.fundCompanyDao = fundCompanyDao;
	}

	public void setFundTypeDao(FundTypeDao fundTypeDao) {
		this.fundTypeDao = fundTypeDao;
	}

	public static List getCompetitionList(String year, int season) {
		return new ArrayList();
	}

	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

	public void setFundAreaDao(FundAreaDao fundAreaDao) {
		this.fundAreaDao = fundAreaDao;
	}

	public void setFundIndustryDao(FundIndustryDao fundIndustryDao) {
		this.fundIndustryDao = fundIndustryDao;
	}

  public void setExchangeRateDao(ExchangeRateDao exchangeRateDao) {
    this.exchangeRateDao = exchangeRateDao;
  }

  public void setResourceLocationDao(ResourceLocationDao resourceLocationDao) {
		this.resourceLocationDao = resourceLocationDao;
	}

    public void setFundEquityDao(FundEquityDao fundEquityDao) {
	this.fundEquityDao = fundEquityDao;
    }

	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

  public void setTradeTimeDao(TradeTimeDao tradeTimeDao) {
    this.tradeTimeDao = tradeTimeDao;
  }

  public void setHolidayDao(HolidayDao holidayDao) {
    this.holidayDao = holidayDao;
  }

  public void setAccountDao(AccountDao accountDao) {
    this.accountDao = accountDao;
  }


    public TimerTaskDao getTimerTaskDao() {
        return timerTaskDao;
    }

    public void setTimerTaskDao(TimerTaskDao timerTaskDao) {
        this.timerTaskDao = timerTaskDao;
    }
}
