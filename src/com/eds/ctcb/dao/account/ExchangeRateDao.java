package com.eds.ctcb.dao.account;

import java.math.BigDecimal;
import java.util.Date;

import com.eds.ctcb.dao.BaseDao;

public interface ExchangeRateDao  extends BaseDao{
	public BigDecimal getExchangeRate(Integer inSCurrencyType, Integer inDCurrencyType,
			Date inDate);

	public BigDecimal getLatestExchangeRate(Integer sCurrencyType, Integer dCurrencyType);

}
