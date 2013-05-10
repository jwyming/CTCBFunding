package com.eds.ctcb.dao.account;

import java.math.BigDecimal;

import com.eds.ctcb.dao.BaseDao;

public interface FundAccountDao  extends BaseDao{
public BigDecimal getNonFrozenFundAmount(String fundCode,Long userId);
public BigDecimal getNonFrozenFundValue(String fundCode,Long userId);

}
