package com.eds.ctcb.dao.system;

import java.util.Date;

import com.eds.ctcb.dao.BaseDao;

/**
 * 
 * @author gzb5dy
 * 
 */
public interface HolidayDao extends BaseDao {
	
	/**
	 * Check the current day if it is just the trade day of the mutual fund. 
	 * @return If the current day is trade day return true; otherwise return
	 *         false.
	 */
	public boolean checkDay();

	/**
	 * Check the given day if it is just the trade day of the mutual fund. 
	 * 
	 * @param inDate
	 * @return If the given day is trade day return true; otherwise return
	 *         false.
	 */
	public boolean checkDay(Date inDate);

	/**
	 * Get the holiday.
	 * 
	 * @param inDate
	 * @return If the given day is holiday return true; otherwise return false.
	 */
	public Integer getHoliday(Date inDate);
}
