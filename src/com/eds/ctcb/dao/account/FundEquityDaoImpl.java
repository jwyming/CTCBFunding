package com.eds.ctcb.dao.account;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.FundEquity;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FundEquityDaoImpl extends BaseDaoImpl implements FundEquityDao {

	public BigDecimal getFundEquity(Long inFundId, Date inDate) {
		String Hql = "from FundEquity as f where f.fund.id=? and trunc(f.time)=trunc(?)";
		QryBean qryBean = new QryBean(Hql, new Object[] { inFundId, inDate });
		List tempList = this.qryInList(qryBean);
		FundEquity fundEquity;
//		System.out.println("list.size=" + tempList.size());
		if (tempList != null && tempList.size() == 1
				&& tempList.get(0) instanceof FundEquity) {
			fundEquity = (FundEquity) (tempList.get(0));
			return fundEquity.getEquity();
		} else {
			return null;
		}

	}

	public List<Date> getAllEquityDate() {

		String Hql = "select distinct f.time from FundEquity as f order by f.time";
		QryBean qryBean = new QryBean(Hql, null);
		List<Date> tempList = this.qryInList(qryBean);
		return tempList;
	}

	private Date getPastDay(Date day, int beforeNum) {
		Calendar c = Calendar.getInstance();
		c.setTime(day);
		int day_of_year = c.get(Calendar.DAY_OF_YEAR);
		day_of_year = day_of_year - beforeNum;
		c.set(Calendar.DAY_OF_YEAR, day_of_year);

		return c.getTime();

	}

	public List getFundEquityTodayAndOld(Long inFundId, int timeRank) {

	
		List<Date> allFundEqutityDayList = this.getAllEquityDate();
//		 if there is no fundEquity information,then return null.
		if (allFundEqutityDayList==null) {
			return null;
		}
		int dayListSize = allFundEqutityDayList.size();
		
		// get the latest day of list
		Date endDay = allFundEqutityDayList.get(dayListSize - 1);

		// compute the initate startDay
		Date startDay = this.getPastDay(endDay, timeRank);

		Date temp;
		if (!allFundEqutityDayList.contains(startDay)) {
			int i = 1;
			temp = allFundEqutityDayList.get(dayListSize - 1);

			while (!temp.before(startDay) && i < dayListSize) {
				i++;
				temp = allFundEqutityDayList.get(dayListSize - i);

			}
			// if look for back
			if (i == dayListSize) {
				int j = 0;
				temp = allFundEqutityDayList.get(j);

				while (!temp.after(startDay) && j < dayListSize) {
					temp = allFundEqutityDayList.get(j++);

				}
			}
			startDay = temp;

		}

		BigDecimal todayEquity = this.getFundEquity(inFundId, endDay);
		BigDecimal oldEquity = this.getFundEquity(inFundId, startDay);
		List resultList = new LinkedList();

		BigDecimal equityArray[] = new BigDecimal[2];
		Date dateArray[] = new Date[2];
		equityArray[0] = todayEquity;
		equityArray[1] = oldEquity;
		resultList.add(equityArray);
		dateArray[0] = endDay;
		dateArray[1] = startDay;
		resultList.add(dateArray);

		return resultList;

	}
	public BigDecimal getLatestEqutiy(Long inFundId){
		List<Date> allFundEqutityDayList = this.getAllEquityDate();
		if(allFundEqutityDayList!=null){
				int dayListSize = allFundEqutityDayList.size();
				
				// get the latest day of list
				Date endDay = allFundEqutityDayList.get(dayListSize - 1);
				return this.getFundEquity(inFundId, endDay);
		}else{
			return null;
		}
	}
}
