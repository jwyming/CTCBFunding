package com.eds.ctcb.biz.deal;

import java.math.BigDecimal;
import java.util.Set;

import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.TradeTaskStatus;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.FundAccount;
import com.eds.ctcb.db.Trade;
import com.eds.ctcb.db.TradeTask;
import com.eds.ctcb.db.User;

public class ChangeFundBizImpl extends BaseBiz implements ChangeFundBiz {

	public void changeFund(Trade changeFund) {
		// if there isn't the frozen fund  account as Source Account,create it
		User user = changeFund.getTradeUser();
        FundAccount non_frozenFundAccount = changeFund.getAccountBySaccountId().getFundAccount();   
        FundAccount frozenFundAccount = null;
        Set<Account> accounts = user.getAccounts();
        for (Account account : accounts) {
           if (account.getType().equals(AccountType.FROZEN_FUND)) {
               FundAccount tempAccount = account.getFundAccount();
               if (tempAccount.getFund().getId().equals(non_frozenFundAccount.getFund().getId())) {
            	   frozenFundAccount = tempAccount;
                   break;
               }
           }
        }
       if (frozenFundAccount == null) {
           // create account
           Account newAccount = new Account();
           newAccount.setCreateTime(changeFund.getCreateTime());
           newAccount.setRemark(null);
           newAccount.setType(AccountType.FROZEN_FUND);
           newAccount.setUser(user);
           this.accountDao.create(newAccount);

           // create fund account
           FundAccount newFundAccount = new FundAccount();
           newFundAccount.setAccount(newAccount);
           newFundAccount.setCount(BigDecimal.ZERO);
           newFundAccount.setFund(non_frozenFundAccount.getFund());
           newFundAccount.setIncomingSet(null);
           newFundAccount.setInitCount(BigDecimal.ZERO);
           this.fundAccountDao.create(newFundAccount);
           frozenFundAccount = newFundAccount;
       }
       
        // update the frozen fund account 
        BigDecimal setCount = changeFund.getCount().add(changeFund.getFee());
		frozenFundAccount.setCount(frozenFundAccount.getCount().add(setCount));
		this.fundAccountDao.update(frozenFundAccount);
		
		// update the non_frozen fund account
		non_frozenFundAccount.setCount(non_frozenFundAccount.getCount().subtract(setCount));
		this.fundAccountDao.update(non_frozenFundAccount);
		
	               
		// add the trade record into trade and trade task table according the investment day
		changeFund.setAccountBySaccountId(frozenFundAccount.getAccount());
        this.tradeDao.create(changeFund);
        // create trade task record in database
        TradeTask tradeTask = new TradeTask();
        tradeTask.setTrade(changeFund);
        tradeTask.setCreateTime(changeFund.getCreateTime());
        tradeTask.setPlanTime(changeFund.getSetTime());
        tradeTask.setStatus(TradeTaskStatus.INITIAL);
        tradeTask.setCount(0);
        tradeTask.setLastTime(null);
        this.tradeTaskDao.create(tradeTask);
		
	}

	
}
