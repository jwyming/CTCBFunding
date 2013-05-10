package com.eds.ctcb.dao.account;

import java.util.List;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.db.FundType;

public interface FundDao  extends BaseDao {
  public List<Fund> getFundsByType(Long fundTypeId);
  public List<Fund> getFundsByCompany(Long companyId);
  public Fund getFundById(Long fundId);
  public Fund getFundByCode(String fundCode);
  public abstract PageBean qryFundPerformanceInPage(QryBean qryBean,int pageSize,int pageNumber);
  public Long getCompanyByFundCode(String fundCode);
}
