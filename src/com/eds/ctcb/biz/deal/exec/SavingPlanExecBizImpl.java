package com.eds.ctcb.biz.deal.exec;

import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.constant.*;
import com.eds.ctcb.db.*;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.exception.BizExceptionCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class SavingPlanExecBizImpl extends BaseBiz implements SavingPlanExecBiz {
    private static LogEx log = new LogEx(SavingPlanExecBizImpl.class);

    synchronized public List<SavingPlan> getUnexecutedSavingPlans() {
        List<SavingPlan> unexecutedSavingPlans = new ArrayList<SavingPlan>();
        List<SavingPlan> savingPlans = this.savingPlanDao.getValidSavingPlans();
        for(SavingPlan savingPlan : savingPlans) {
            Account destinationAccount = findSavingPlanFundAccount(savingPlan);
            List<Trade> list = this.tradeDao.findCurrentMonthInvestment(savingPlan.getAccount(), destinationAccount, savingPlan.getTradeType());
            if(list == null || list.size() == 0) {
                unexecutedSavingPlans.add(savingPlan);
            }
        }
        return unexecutedSavingPlans;
    }

    synchronized public boolean executeSavingPlan(SavingPlan savingPlan) throws BizException {
        Integer tradeType = savingPlan.getTradeType();
        if (tradeType.equals(TradeType.REGULAR_INVESTMENT)) {
            // create trade record in database
            Trade trade = new Trade();
            trade.setTradeUser(savingPlan.getAccount().getUser());
            trade.setType(savingPlan.getTradeType());
            trade.setAccountBySaccountId(savingPlan.getAccount());
            Account dAccount = findSavingPlanFundAccount(savingPlan);
            trade.setAccountByDaccountId(dAccount);
            trade.setTradeMode(TradeMode.MONEY);
            trade.setCount(savingPlan.getCount());
            trade.setCurrency(savingPlan.getCurrency());
            trade.setIncomingSet(savingPlan.getIncomingSet());
            Calendar now = Calendar.getInstance();
            now.setTime(this.tradeTaskDao.getSysdate());
            trade.setCreateTime(now.getTime());
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
        } else if (tradeType.equals(TradeType.IRREGULAR_INVESTMENT)) {
            // create trade record in database
            Trade trade = new Trade();
            trade.setTradeUser(savingPlan.getAccount().getUser());
            trade.setType(savingPlan.getTradeType());
            trade.setAccountBySaccountId(savingPlan.getAccount());
            Account dAccount = findSavingPlanFundAccount(savingPlan);
            trade.setAccountByDaccountId(dAccount);
            trade.setTradeMode(TradeMode.MONEY);
            // compute the trade amount according to the change rule
            Calendar now = Calendar.getInstance();
            now.setTime(this.tradeTaskDao.getSysdate());
            Calendar createTime = Calendar.getInstance();
            createTime.setTime(savingPlan.getCreateTime());
            int monthDiff = (now.get(Calendar.YEAR) - createTime.get(Calendar.YEAR)) * 12 + (now.get(Calendar.MONTH) - createTime.get(Calendar.MONTH));
            BigDecimal variable = savingPlan.getCount().multiply(savingPlan.getRate()).divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP);
            BigDecimal tradeAmount = savingPlan.getCount().add(variable.multiply(new BigDecimal(monthDiff)));
            if(tradeAmount.compareTo(BigDecimal.ZERO) <= 0) {
                savingPlan.setStatus(SavingPlanStatus.INVALID);
                savingPlan.setUpdateTime(this.tradeDao.getSysdate());
                this.savingPlanDao.update(savingPlan);
                log.error("the irregular investment trade amount is zero, stopping investment automatically");
                throw new BizException(BizExceptionCode.TRADE_AMOUNT_INVALID,
                    "trade.tradeAmount.invalid", new Object[]{savingPlan.getId()}, null);
            }
            trade.setCount(tradeAmount);
            trade.setCurrency(savingPlan.getCurrency());
            trade.setIncomingSet(savingPlan.getIncomingSet());
            trade.setCreateTime(now.getTime());
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
        } else {
            log.error("Wrong trade type in saving plan tables");
            throw new BizException(BizExceptionCode.TRADE_TYPE_UNEXPECT,
                    "trade.tradetype.unexpect", new Object[]{savingPlan.getId()}, null);
        }
        return true;
    }

    private Account findSavingPlanFundAccount(SavingPlan savingPlan) {
        Account dAccount = null;
        Set<Account> accounts = savingPlan.getAccount().getUser().getAccounts();
        for (Account account : accounts) {
            FundAccount fundAccount = account.getFundAccount();
            if (fundAccount != null) {
                if (fundAccount.getFund().getId().equals(savingPlan.getFund().getId())) {
                    dAccount = account;
                    break;
                }
            }
        }
        return dAccount;
    }

}
