package com.eds.ctcb.dao.account;

import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.SysParam;

public class CurrencyDaoImpl extends BaseDaoImpl implements CurrencyDao {
	public List<Currency> getAllCurrency(){
		String hql=new String("from Currency as t order by t.type asc");
		QryBean qryBean=new QryBean(hql,null);
		return this.qryInList(qryBean);
	}
	
	public List<Currency> getNtdOnly(){
		String hql=new String("from Currency as t where t.type=?");
		QryBean qryBean=new QryBean(hql,new Object[]{new Integer(1)});
		return this.qryInList(qryBean);
	}

  public Currency getCurrencyById(Long currencyId) {
    return (Currency) this.findById(Currency.class, currencyId);
  }
  
  public Currency getCurrencyByType(int type) {
	   	 Currency currency= null ;
	         String hql="from Currency as c where c.type=?";
	         QryBean qryBean=new QryBean(hql,new Object[]{new Integer(type)});
	         List tempList = this.qryInList(qryBean);
	   		if(tempList!=null && tempList.size()==1 && tempList.get(0) instanceof Currency){
	   			currency =(Currency)(tempList.get(0));
	   		}
	         return currency;
	   	
  }
}
