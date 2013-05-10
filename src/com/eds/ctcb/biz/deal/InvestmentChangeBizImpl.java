package com.eds.ctcb.biz.deal;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.constant.SavingPlanStatus;
import com.eds.ctcb.db.SavingPlan;

public class InvestmentChangeBizImpl extends BaseBiz implements
		InvestmentChangeBiz {

	public SavingPlan findInvestmentById(Long id) {
		return this.savingPlanDao.getSavingPlanById(id);
	}

	public PageBean qryRegularInvestmentInPage(Long userId, int pageSize, int pageNumber,String sort,String order) {
		return this.savingPlanDao.getRegularInvestmentInPage(userId, pageSize, pageNumber,sort,order);
	}
	
	public PageBean qryIrregularInvestmentInPage(Long userId, int pageSize, int pageNumber,String sort,String order) {
		return this.savingPlanDao.getIrregularInvestmentInPage(userId, pageSize, pageNumber,sort,order);
	}


	public void investmentChange(SavingPlan savingPlan) {
		this.savingPlanDao.update(savingPlan);
	}

	public void reInvest(SavingPlan savingPlan) {
		savingPlan.setStatus(SavingPlanStatus.VALID);
		this.savingPlanDao.update(savingPlan);
	}

	public void stopInvest(SavingPlan savingPlan) {
		savingPlan.setStatus(SavingPlanStatus.INVALID);
		this.savingPlanDao.update(savingPlan);
	}

}
