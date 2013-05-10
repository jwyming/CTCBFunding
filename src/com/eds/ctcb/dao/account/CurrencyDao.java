package com.eds.ctcb.dao.account;

import java.util.List;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.Currency;

public interface CurrencyDao extends BaseDao {

	public List<Currency> getAllCurrency();
	
	public List<Currency> getNtdOnly();

	public Currency getCurrencyById(Long currencyId);
  
	public Currency getCurrencyByType(int type);
}
