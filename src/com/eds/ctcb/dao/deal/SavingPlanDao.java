package com.eds.ctcb.dao.deal;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.SavingPlan;

import java.util.List;

public interface SavingPlanDao extends BaseDao{
	public List<SavingPlan> getValidSavingPlans();
	public SavingPlan getSavingPlanById(Long savingPlanId);
	public List<SavingPlan> getSavingPlansByFundId(Long fundId);
    public PageBean getRegularInvestmentInPage(Long userId, int pageSize, int pageNumber,String sort,String order);
    public PageBean getIrregularInvestmentInPage(Long userId, int pageSize, int pageNumber,String sort,String order);
}
