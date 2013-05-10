package com.eds.ctcb.dao.deal;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.constant.SavingPlanStatus;
import com.eds.ctcb.constant.TradeType;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.SavingPlan;

import java.util.List;
import java.util.Set;

public class SavingPlanDaoImpl extends BaseDaoImpl implements SavingPlanDao {
	
	public List<SavingPlan> getValidSavingPlans() {
		String hql = "from SavingPlan as tt where tt.status = ?";
		QryBean qryBean = new QryBean(hql, new Object[]{SavingPlanStatus.VALID});
		List list = this.qryInList(qryBean);
        for (Object aList : list) {
            SavingPlan savingPlan = (SavingPlan) aList;
            Set<Account> accounts = savingPlan.getAccount().getUser().getAccounts();
            for(Account account : accounts) {
                Integer accountType = account.getType();
                if(accountType == AccountType.FROZEN_FUND || accountType == AccountType.NON_FROZEN_FUND) {
                    account.getFundAccount().getFund().getId();
                    account.getFundAccount().getAccount().getUser().getAccounts().size();
                } else if(accountType == AccountType.FROZEN_CASH || accountType == AccountType.NON_FROZEN_CASH) {
                    account.getCashAccount().getCount();
                }
            }
            savingPlan.getFund().getId();
            savingPlan.getCurrency().getId();
        }
        return list;
	}
	
	public List<SavingPlan> getSavingPlansByFundId(Long fundId) {
		String hql = "from SavingPlan as tt where tt.fund.id = ?";
		QryBean qryBean = new QryBean(hql, new Object[]{fundId});
		List list = this.qryInList(qryBean);
        for (Object aList : list) {
            SavingPlan savingPlan = (SavingPlan) aList;
            savingPlan.getAccount().getId();
        }
        return list;
	}

    public PageBean getRegularInvestmentInPage(Long userId, int pageSize, int pageNumber,String sort, String order) {
    	StringBuffer tempHql = new StringBuffer( "from SavingPlan as tt where tt.tradeType = ? and tt.account.user.id = ?" );
       if(sort != null){
    	   tempHql.append(" order by tt."+ sort);
       }
       if(order != null){
    	   tempHql.append(" "+order);
       }
        
        String hql = tempHql.toString();
        Object[] paramsArray = new Object[]{TradeType.REGULAR_INVESTMENT, userId};
        QryBean qryBean = new QryBean(hql, paramsArray);
		PageBean pageBean = this.qryInPage(qryBean, pageSize, pageNumber);
        for(SavingPlan savingPlan : (List<SavingPlan>) pageBean.getList()) {
            savingPlan.getFund().getCode();
        }
        return pageBean;
    }

	public PageBean getIrregularInvestmentInPage(Long userId, int pageSize, int pageNumber,String sort,String order) {
		StringBuffer tempHql = new StringBuffer("from SavingPlan as tt where tt.tradeType = ? and  tt.account.user.id = ?");
		if(sort != null){
	    	   tempHql.append(" order by tt."+ sort);
	       }
	       if(order != null){
	    	   tempHql.append(" "+order);
	       }
	       String hql = tempHql.toString();
        Object[] paramsArray = new Object[]{TradeType.IRREGULAR_INVESTMENT, userId};
        QryBean qryBean = new QryBean(hql, paramsArray);
		PageBean pageBean = this.qryInPage(qryBean, pageSize, pageNumber);
        for(SavingPlan savingPlan : (List<SavingPlan>) pageBean.getList()) {
            savingPlan.getFund().getCode();
        }
        return pageBean;
	}

	public SavingPlan getSavingPlanById(Long savingPlanId) {
		return (SavingPlan) this.findById(SavingPlan.class, savingPlanId);
	}
}
