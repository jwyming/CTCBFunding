package com.eds.ctcb.dao.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.ExchangeRate;

public class ExchangeRateDaoImpl extends BaseDaoImpl implements ExchangeRateDao {

	public BigDecimal getLatestExchangeRate(Integer sCurrencyType, Integer dCurrencyType) {
		if(sCurrencyType.equals(dCurrencyType)){
			return BigDecimal.ONE;
		}
		String Hql = "select tt.exchangeRate from ExchangeRate as tt where tt.scurrency.type=? and tt.dcurrency.type=? order by tt.updateTime desc";
		QryBean qryBean = new QryBean(Hql, new Object[] { sCurrencyType,
				dCurrencyType });
		List exchangeRateList=this.qryInList(qryBean);
		if(exchangeRateList.size()!=0){
			return (BigDecimal)exchangeRateList.get(0);
		}else{
			return null;
		}
	}

	public BigDecimal getExchangeRate(Integer inSCurrencyType, Integer inDCurrencyType,
			Date inDate) {
		if(inSCurrencyType==inDCurrencyType){
			return BigDecimal.ONE;
		}
		String Hql = "from ExchangeRate as e where e.scurrency.type=? and e.dcurrency.type=? and trunc(e.updateTime)=trunc(?)";
		QryBean qryBean = new QryBean(Hql, new Object[] { inSCurrencyType,
				inDCurrencyType, inDate });
		List tempList = this.qryInList(qryBean);
        ExchangeRate exchangeRate = null;
        if (tempList != null && tempList.size() == 1
				&& tempList.get(0) instanceof ExchangeRate) {
			exchangeRate = (ExchangeRate) (tempList.get(0));
		}
        if(exchangeRate != null) {
            return exchangeRate.getExchangeRate();
        } else {
            return null;
        }
    }

}
