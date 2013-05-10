package com.eds.ctcb.dao.system;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.TradeTime;

/**
 * 
 * @author gzb5dy
 *
 */
public class TradeTimeDaoImpl extends BaseDaoImpl implements TradeTimeDao {
	
	/**
	 * Check the current time if it is just the trade time of the mutual fund.
	 * 
	 * @param tradeType
	 * @param fundType
	 * @param fundArea
	 * @param fundId
	 * @return If the current time is trade time return true; otherwise return
	 *         false.
	 */
	public boolean checkTime(Integer tradeType, Long fundType, Long fundArea,
			Long fundId) {
		return(checkTime(tradeType, fundType, fundArea, fundId, this.getSysdate()));
			
	
	}

	/**
	 * Check the given time if it is just the trade time of the mutual fund.
	 * 
	 * @param tradeType
	 * @param fundType
	 * @param fundArea
	 * @param fundId
	 * @param inTime
	 * @return If the given time is trade time return true; otherwise return
	 *         false.
	 */
	public boolean checkTime(Integer tradeType, Long fundType, Long fundArea,
			Long fundId, Date inTime) {
		Calendar now = Calendar.getInstance();
		now.setTime(inTime);
		Integer inTimeSecond = now.get(Calendar.SECOND);
		Integer inTimeMinute = now.get(Calendar.MINUTE);
		Integer inTimeHour   = now.get(Calendar.HOUR_OF_DAY);
		String innerTime = inTimeHour.toString()+":"+inTimeMinute.toString()+":"+inTimeSecond.toString();
		String Hql = "from TradeTime as t where t.tradeType=? and t.fundType=? and t.fundArea=? and fundId=? and (to_date(?,'hh24:mi:ss') between t.beginTime and t.endTime)";
		QryBean qryBean = new QryBean(Hql, new Object[] { tradeType,
				fundType, fundArea, fundId, innerTime});
		List tempList = this.qryInList(qryBean);
		return (tempList != null && tempList.size() == 1
				&& tempList.get(0) instanceof TradeTime);
	}

}
