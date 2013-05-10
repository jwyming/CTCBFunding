package com.eds.ctcb.biz.deal;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.TradeMode;
import com.eds.ctcb.constant.TradeStatus;
import com.eds.ctcb.constant.TradeTaskStatus;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.FundAccount;
import com.eds.ctcb.db.SavingPlan;
import com.eds.ctcb.db.Trade;
import com.eds.ctcb.db.TradeTask;
import com.eds.ctcb.db.User;

public class IrregularInvestmentBizImpl extends BaseBiz implements
		IrregularInvestmentBiz {

	public void irregularInvestment(SavingPlan irregularInvestment) {


	        // add this investment plan into the savingPlan table
	        this.savingPlanDao.create(irregularInvestment);
	        // if there haven't the fund account ,create it
	        FundAccount fundAccount = null;
	        User user = irregularInvestment.getAccount().getUser();
	        Set<Account> accounts = user.getAccounts();
	        for (Account account : accounts) {
	            if (account.getType().equals(AccountType.NON_FROZEN_FUND)) {
	                FundAccount tempAccount = account.getFundAccount();
	                if (tempAccount.getFund().getId().equals(irregularInvestment.getFund().getId())) {
	                    fundAccount = tempAccount;
	                    break;
	                }
	            }
	        }
	        if (fundAccount == null) {
	            // create account
	            Account account = new Account();
	            account.setCreateTime(irregularInvestment.getCreateTime());
	            account.setRemark(null);
	            account.setType(AccountType.NON_FROZEN_FUND);
	            account.setUser(user);
	            this.accountDao.create(account);

	            // create fund account
	            FundAccount newFundAccount = new FundAccount();
	            newFundAccount.setAccount(account);
	            newFundAccount.setCount(new BigDecimal(0));
	            newFundAccount.setFund(irregularInvestment.getFund());
	            newFundAccount.setIncomingSet(irregularInvestment.getIncomingSet());
	            newFundAccount.setInitCount(irregularInvestment.getCount());
	            this.fundAccountDao.create(newFundAccount);
	            fundAccount = newFundAccount;
	        }
	        //
	        // add the trade record into trade and trade task table according the investment day
	        Calendar now = Calendar.getInstance();
	        now.setTime(irregularInvestment.getCreateTime());
	        int today = now.get(Calendar.DAY_OF_MONTH);
	        if (today < irregularInvestment.getDay()) {
	            // create trade record in database
	            Trade trade = new Trade();
	            trade.setTradeUser(user);
	            trade.setType(irregularInvestment.getTradeType());
	            trade.setAccountBySaccountId(irregularInvestment.getAccount());
	            trade.setAccountByDaccountId(fundAccount.getAccount());
	            trade.setTradeMode(TradeMode.MONEY);
	            trade.setCount(irregularInvestment.getCount());
	            trade.setCurrency(irregularInvestment.getCurrency());
	            trade.setIncomingSet(irregularInvestment.getIncomingSet());
	            trade.setCreateTime(irregularInvestment.getCreateTime());
	            trade.setStatus(TradeStatus.UNEXECUTED);
	            Calendar settingTime = Calendar.getInstance();
	            settingTime.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), irregularInvestment.getDay());
	            trade.setSetTime(settingTime.getTime());
	            trade.setTradeTime(null);
	            BigDecimal handlingTariff = this.sysParamDao.getHandlingTariff(irregularInvestment.getCount());
	            trade.setFee(handlingTariff);
	            this.tradeDao.create(trade);

	            // create trade task record in database
	            TradeTask tradeTask = new TradeTask();
	            tradeTask.setTrade(trade);
	            tradeTask.setCreateTime(now.getTime());
	            tradeTask.setPlanTime(settingTime.getTime());
	            tradeTask.setStatus(TradeTaskStatus.INITIAL);
	            tradeTask.setCount(0);
	            tradeTask.setLastTime(null);
	            this.tradeTaskDao.create(tradeTask);

            }
    }


}
