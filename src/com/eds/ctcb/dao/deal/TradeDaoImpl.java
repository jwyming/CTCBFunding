package com.eds.ctcb.dao.deal;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.constant.TradeStatus;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.Trade;

import java.util.Date;
import java.util.List;

public class TradeDaoImpl extends BaseDaoImpl implements TradeDao {

	/*
	 * (non-Javadoc)
	 * @see com.eds.ctcb.dao.deal.TradeDao#getNotExecTrade(java.lang.Long)
	 */
	public List getNotExecTrade(Long userId) {

		String hql = "from Trade as t where t.tradeUser.id=? and t.status=" + TradeStatus.UNEXECUTED;
		QryBean qryBean = new QryBean(hql, new Object[]{userId});
		return this.qryInList(qryBean);
	}


	public PageBean getNotExcuteTrade(Long userId, int pageSize, int page){
		String hql = "from Trade as t where t.tradeUser.id=? and t.status=" + TradeStatus.UNEXECUTED;
		QryBean qryBean = new QryBean(hql, new Object[]{userId});		
		return this.qryInPage(qryBean, pageSize, page);
	}

    public List<Trade> findCurrentMonthInvestment(Account sourceAccount, Account destinationAccount, Integer tradeType) {
        String hql = "from Trade as t where t.accountBySaccountId.id=? " +
                " and t.accountByDaccountId.id = ? and t.type= ? " +
                " and to_char(t.setTime, 'yyyy/mm') = to_char(?, 'yyyy/mm')";
		QryBean qryBean = new QryBean(hql, new Object[]{sourceAccount.getId(), destinationAccount.getId(), tradeType,
                this.getSysdate()});
		return this.qryInList(qryBean);
    }


}
