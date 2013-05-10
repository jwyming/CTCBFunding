package com.eds.ctcb.dao.account;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Fund;

import java.util.List;

public interface AccountDao extends BaseDao{

	

	List<Account> getAllAccountByUserId(Long custId);
	List<Account> getNotFrozenFundAccountByUserId(Long custId);
	String getCashAccountCurrency(Long custId);
}
