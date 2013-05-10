package com.eds.ctcb.biz.deal;

import java.math.BigDecimal;
import java.util.Set;

import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.TradeTaskStatus;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.CashAccount;
import com.eds.ctcb.db.FundAccount;
import com.eds.ctcb.db.Trade;
import com.eds.ctcb.db.TradeTask;
import com.eds.ctcb.db.User;

public class SingleInvestmentBizImpl extends BaseBiz implements SingleInvestmentBiz {
	public void singleInvestment(Trade singleInvestment) {
		// find the frozenCashAccount by the non_frozenCashAccount
		CashAccount non_frozenCashAccount = singleInvestment.getAccountBySaccountId().getCashAccount();
		CashAccount frozenCashAccount = new CashAccount();
		User user = non_frozenCashAccount.getAccount().getUser();
		Set<Account> accounts = user.getAccounts();
		for (Account account : accounts) {
            if (account.getType().equals(AccountType.FROZEN_CASH)) {
            	CashAccount tempCashAccount = account.getCashAccount();
            	if(tempCashAccount.getCurrency().getId().equals(singleInvestment.getCurrency().getId())){
            		frozenCashAccount = tempCashAccount;
                     break;
            	}
            }      
        }
		//update the frozen cash account 
		BigDecimal setCount = singleInvestment.getCount().add(singleInvestment.getFee());
		frozenCashAccount.setCount(frozenCashAccount.getCount().add(setCount));
		this.cashAccountDao.update(frozenCashAccount);
		
		// update the non_frozen cash account
		non_frozenCashAccount.setCount(non_frozenCashAccount.getCount().subtract(setCount));
		this.cashAccountDao.update(non_frozenCashAccount);
		

        // add the trade record into trade and trade task table according the investment day
		singleInvestment.setAccountBySaccountId(frozenCashAccount.getAccount());
        this.tradeDao.create(singleInvestment);
        TradeTask tradeTask = new TradeTask();
        tradeTask.setTrade(singleInvestment);
        tradeTask.setCreateTime(singleInvestment.getCreateTime());
        tradeTask.setPlanTime(singleInvestment.getSetTime());
        tradeTask.setStatus(TradeTaskStatus.INITIAL);
        tradeTask.setCount(0);
        tradeTask.setLastTime(null);
        this.tradeTaskDao.create(tradeTask);
            

	}


	

}
