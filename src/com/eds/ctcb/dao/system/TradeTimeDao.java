package com.eds.ctcb.dao.system;

import java.util.Date;

import com.eds.ctcb.dao.BaseDao;

/**
 * 
 * @author gzb5dy
 * 
 */
public interface TradeTimeDao extends BaseDao {

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
			Long fundId);

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
			Long fundId, Date inTime);

}
