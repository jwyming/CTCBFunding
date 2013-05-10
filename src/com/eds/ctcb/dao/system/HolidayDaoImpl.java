package com.eds.ctcb.dao.system;

import java.util.Date;
import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.constant.HolidayStatus;
import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.Holiday;

/**
 * 
 * @author gzb5dy
 *
 */
public class HolidayDaoImpl extends BaseDaoImpl implements HolidayDao {
	
	/**
	 * Check the current day if it is just the trade day of the mutual fund. 
	 * @return If the current day is trade day return true; otherwise return
	 *         false.
	 */
	public boolean checkDay() {
		return checkDay(this.getSysdate());

	}

	/**
	 * Check the given day if it is just the trade day of the mutual fund.
	 * 
	 * @param inDate
	 * @return If the given day is trade day return true; otherwise return
	 *         false.
	 */
	public boolean checkDay(Date inDate) {
		String hql = "from Holiday as h where trunc(h.day)=trunc(?) and h.state=?";
		QryBean qryBean = new QryBean(hql, new Object[] { inDate,
				new Integer(HolidayStatus.HOLIDAY) });
		List tempList = this.qryInList(qryBean);
		return (tempList != null && tempList.size() == 1 && tempList.get(0) instanceof Holiday);
	}

	/**
	 * Get the holiday.
	 * 
	 * @param inDate
	 * @return If the given day is holiday return true; otherwise return false.
	 */
	public Integer getHoliday(Date inDate) {
		String hql = "from Holiday as h where trunc(h.day)=trunc(?)";
		QryBean qryBean = new QryBean(hql, new Object[] { inDate });
		List tempList = this.qryInList(qryBean);
		Integer state = null;
		if(tempList != null && tempList.size() == 1 && tempList.get(0) instanceof Holiday)
			state = ((Holiday) tempList.get(0)).getState();
		return state;
	}

}
