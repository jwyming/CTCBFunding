package com.eds.ctcb.dao.account;

import java.util.List;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.Fund;

public class FundDaoImpl extends BaseDaoImpl implements FundDao {
	

	public List<Fund> getFundsByType(Long fundTypeId) {
		String hql = "from Fund as tt where tt.type = ?";
		QryBean qryBean = new QryBean(hql, new Object[]{fundTypeId});
		return this.qryInList(qryBean);
	}
	
	public List<Fund> getFundsByCompany(Long companyId) {
		String hql = "from Fund as tt where tt.company = ?";
		QryBean qryBean = new QryBean(hql, new Object[]{companyId});
		return this.qryInList(qryBean);
	}

  public Fund getFundById(Long fundId) {
		return (Fund) this.findById(Fund.class, fundId);
  }

  public Fund getFundByCode(String fundCode) {
		String hql = "from Fund as tt where tt.code = ?";
		QryBean qryBean = new QryBean(hql, new Object[]{fundCode});
		List funds = this.qryInList(qryBean);
    if(funds != null && funds.size() > 0) {
      return (Fund) funds.get(0);
    } else {
      return null;
    }
  }

	public PageBean qryFundPerformanceInPage(QryBean qryBean, int pageSize, int pageNumber) {
		return this.qryInPage(qryBean, pageSize, pageNumber);
	}

	public Long getCompanyByFundCode(String fundCode) {
		String hql = "from Fund as tt where tt.code = ?";
		QryBean qryBean = new QryBean(hql, new Object[]{fundCode});		
		List fund = this.qryInList(qryBean);
		Long companyId = 0L;
		if(fund != null && fund.size() == 1 && fund.get(0) instanceof Fund ){
			 companyId = ((Fund)fund.get(0)).getCompany().getId();
		}
		
		return companyId;
	}
}
