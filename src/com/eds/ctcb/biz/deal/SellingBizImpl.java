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

public class SellingBizImpl extends BaseBiz implements SellingBiz {

	public void selling(Trade sellingTrade) {
		FundAccount non_frozenFundAccount = sellingTrade.getAccountBySaccountId().getFundAccount();
		
		// if there haven't the frozenFund and non_frozenFund account ,create it
		User user = sellingTrade.getTradeUser();
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
           Account account = new Account();
           account.setCreateTime(sellingTrade.getCreateTime());
           account.setRemark(null);
           account.setType(AccountType.FROZEN_FUND);
           account.setUser(user);
           this.accountDao.create(account);

           // create fund account
           FundAccount newFundAccount = new FundAccount();
           newFundAccount.setAccount(account);
           newFundAccount.setCount(BigDecimal.ZERO);
           newFundAccount.setFund(sellingTrade.getAccountBySaccountId().getFundAccount().getFund());
           newFundAccount.setIncomingSet(sellingTrade.getIncomingSet());
           newFundAccount.setInitCount(BigDecimal.ZERO);
           this.fundAccountDao.create(newFundAccount);
           frozenFundAccount = newFundAccount;
       }
       
		
		
		//update the freeze fund account 
		BigDecimal setCount = sellingTrade.getCount().add(sellingTrade.getFee());
		frozenFundAccount.setCount(frozenFundAccount.getCount().add(setCount));
		this.fundAccountDao.update(frozenFundAccount);
		
		// update the normal fund account
		non_frozenFundAccount.setCount(non_frozenFundAccount.getCount().subtract(setCount));
		this.fundAccountDao.update(non_frozenFundAccount);
		// add the trade record into trade and trade task table according the investment day
		sellingTrade.setAccountBySaccountId(frozenFundAccount.getAccount());
        this.tradeDao.create(sellingTrade);
        TradeTask tradeTask = new TradeTask();
        tradeTask.setTrade(sellingTrade);
        tradeTask.setCreateTime(sellingTrade.getCreateTime());
        tradeTask.setPlanTime(sellingTrade.getSetTime());
        tradeTask.setStatus(TradeTaskStatus.INITIAL);
        tradeTask.setCount(0);
        tradeTask.setLastTime(null);
        this.tradeTaskDao.create(tradeTask);

	}

	
}
