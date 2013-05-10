package com.eds.ctcb.biz.deal;

import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.TradeMode;
import com.eds.ctcb.constant.TradeStatus;
import com.eds.ctcb.constant.TradeTaskStatus;
import com.eds.ctcb.db.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

public class RegularInvestmentBizImpl extends BaseBiz implements
        RegularInvestmentBiz {

    public void regularInvestment(SavingPlan savingPlan) {
        // add this investment plan into the savingPlan table
        this.savingPlanDao.create(savingPlan);
        // if there haven't the fund account ,create it
        FundAccount fundAccount = null;
        User user = savingPlan.getAccount().getUser();
        Set<Account> accounts = user.getAccounts();
        for (Account account : accounts) {
            if (account.getType().equals(AccountType.NON_FROZEN_FUND)) {
                FundAccount tempAccount = account.getFundAccount();
                if (tempAccount.getFund().getId().equals(savingPlan.getFund().getId())) {
                    fundAccount = tempAccount;
                    break;
                }
            }
        }
        if (fundAccount == null) {
            // create account
            Account account = new Account();
            account.setCreateTime(savingPlan.getCreateTime());
            account.setRemark(null);
            account.setType(AccountType.NON_FROZEN_FUND);
            account.setUser(user);
            this.accountDao.create(account);
            // create fund account
            FundAccount newFundAccount = new FundAccount();
            newFundAccount.setAccount(account);
            newFundAccount.setCount(BigDecimal.ZERO);
            newFundAccount.setFund(savingPlan.getFund());
            newFundAccount.setIncomingSet(savingPlan.getIncomingSet());
            newFundAccount.setInitCount(BigDecimal.ZERO);
            this.fundAccountDao.create(newFundAccount);
            fundAccount = newFundAccount;
        }

        // add the trade record into trade and trade task table according the investment day
        Calendar now = Calendar.getInstance();
        now.setTime(savingPlan.getCreateTime());
        int today = now.get(Calendar.DAY_OF_MONTH);
        if (today < savingPlan.getDay()) {
            // create trade record in database
            Trade trade = new Trade();
            trade.setTradeUser(user);
            trade.setType(savingPlan.getTradeType());
            trade.setCurrency(savingPlan.getCurrency());
            trade.setAccountBySaccountId(savingPlan.getAccount());
            trade.setAccountByDaccountId(fundAccount.getAccount());
            trade.setTradeMode(TradeMode.MONEY);
            trade.setCount(savingPlan.getCount());
            trade.setIncomingSet(savingPlan.getIncomingSet());
            trade.setCreateTime(savingPlan.getCreateTime());
            trade.setStatus(TradeStatus.UNEXECUTED);
            Calendar settingTime = Calendar.getInstance();
            settingTime.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), savingPlan.getDay());
            trade.setSetTime(settingTime.getTime());
            trade.setTradeTime(null);
            BigDecimal handlingTariff = this.sysParamDao.getHandlingTariff(savingPlan.getCount());
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
