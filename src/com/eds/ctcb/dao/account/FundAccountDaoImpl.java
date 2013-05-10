package com.eds.ctcb.dao.account;

import java.math.BigDecimal;
import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.FundAccount;

public class FundAccountDaoImpl extends BaseDaoImpl implements FundAccountDao {

	public BigDecimal getNonFrozenFundAmount(String fundCode, Long userId) {
		String hql = "from FundAccount as fa where fa.account.user.id = ? and fa.fund.code = ? and fa.account.type = ?";
		QryBean qryBean = new QryBean(hql, new Object[]{userId,fundCode,AccountType.NON_FROZEN_FUND});
		List fundAccount = this.qryInList(qryBean);
		BigDecimal fundCount = BigDecimal.ZERO;
		if(fundAccount != null && fundAccount.size() == 1 && fundAccount.get(0) instanceof FundAccount){
		
			fundCount = ((FundAccount) fundAccount.get(0)).getCount();
		}
		return fundCount;
	}

	public BigDecimal getNonFrozenFundValue(String fundCode, Long userId) {
		String hql = "from FundAccount as fa where fa.account.user.id = ? and fa.fund.code = ? and fa.account.type = ?";
		QryBean qryBean = new QryBean(hql, new Object[]{userId,fundCode,AccountType.NON_FROZEN_FUND});
		List fundAccount = this.qryInList(qryBean);
		BigDecimal fundValue = BigDecimal.ZERO;
		if(fundValue != null && fundAccount.size() == 1 && fundAccount.get(0) instanceof FundAccount){
		
			fundValue = ((FundAccount) fundAccount.get(0)).getInitCount();
		}
		return fundValue;
	}
	
	


}
