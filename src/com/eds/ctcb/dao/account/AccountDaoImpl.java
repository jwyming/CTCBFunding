package com.eds.ctcb.dao.account;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.db.FundAccount;
import com.eds.ctcb.constant.AccountType;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Calendar;

public class AccountDaoImpl extends BaseDaoImpl implements AccountDao {

	
	

	public List<Account> getAllAccountByUserId(Long custId) {
		String hql = "from Account as tt where tt.user.id = ?";
		QryBean qryBean = new QryBean(hql, new Object[]{custId});
		
		List<Account> custAccountList=this.qryInList(qryBean);
		return custAccountList;
		
	}
	public List<Account> getNotFrozenFundAccountByUserId(Long custId) {
		String hql = "from Account as tt where tt.user.id = ? and tt.type=? and tt.fundAccount.count > 0";
		QryBean qryBean = new QryBean(hql, new Object[]{custId,AccountType.NON_FROZEN_FUND});
		
		List<Account> custAccountList=this.qryInList(qryBean);
		return custAccountList;
	}
	public String getCashAccountCurrency(Long custId) {
		String hql = "select tt.cashAccount.currency.name from Account as tt where tt.user.id = ? and tt.type=?";
		QryBean qryBean = new QryBean(hql, new Object[]{custId,AccountType.NON_FROZEN_CASH});
		
		List custAccountList=this.qryInList(qryBean);
		
			    if(custAccountList.size()!=0){
			 	     return (String)custAccountList.get(0);
			    }else{
			          return null;
		         }
		}
	
}
