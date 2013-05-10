package com.eds.ctcb.biz.deal;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.db.SavingPlan;

public interface InvestmentChangeBiz {
	public PageBean qryRegularInvestmentInPage(Long userId,int pageSize, int pageNumber,String sort,String order);	
	public PageBean qryIrregularInvestmentInPage(Long userId,int pageSize, int pageNumber,String sort,String order);		
	public SavingPlan findInvestmentById(Long id);		
	public void investmentChange(SavingPlan savingPlan);
	public void reInvest(SavingPlan savingPlan);
	public void stopInvest(SavingPlan savingPlan);
}
