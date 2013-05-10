package com.eds.ctcb.dao.account;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.constant.SavingPlanStatus;
import com.eds.ctcb.dao.BaseDaoImpl;
import java.util.List;
import com.eds.ctcb.db.FundType;

public class FundTypeDaoImpl extends BaseDaoImpl implements FundTypeDao {
	public List<FundType> getAllFundType() {
		String hql = "from FundType";
		QryBean qryBean = new QryBean(hql, new Object[]{});
		return this.qryInList(qryBean);
	}
}
