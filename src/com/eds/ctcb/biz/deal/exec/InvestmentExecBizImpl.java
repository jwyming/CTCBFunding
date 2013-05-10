package com.eds.ctcb.biz.deal.exec;

import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.constant.*;
import com.eds.ctcb.dao.deal.TradeDao;
import com.eds.ctcb.dao.deal.TradeTaskDao;
import com.eds.ctcb.db.*;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.exception.BizExceptionCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.math.BigDecimal;

public class InvestmentExecBizImpl extends BaseBiz implements InvestmentExecBiz {
    private static LogEx log = new LogEx(InvestmentExecBizImpl.class);


    /**
     * get the tasks list that task status isn't success and the schedule time is before now, and the
     * executed times is less than the specified limited times.
     *
     * @return List the un-executed trade task list
     */
    public List<TradeTask> getUnexecutedTasks() throws BizException {
        List<TradeTask> unexecutedTasks = new ArrayList<TradeTask>();
        List<TradeTask> list = this.tradeTaskDao.getUnexecutedTasks();
        if (list != null) {
            unexecutedTasks = list;
        }
        return unexecutedTasks;
    }

    /**
     * estimate whether the specified task can be execute now or not
     *
     * @param tradeTask the trade task that will be asserted whether if can be execute now
     * @return true - can execute now; false - cannot execute now, maybe can execute later.
     */
    public boolean canExecutedNow(TradeTask tradeTask) throws BizException {
        Trade trade = tradeTask.getTrade();
        Integer tradeType = trade.getType();
        Fund fund;
        /*
           * for the trade type of single investment, regular investment and irregular investment,
           * we will estimate whether the destination account(generally is fund account)
           * can be traded now or not, and generally their source accounts are cash account.
          */
        if (tradeType.equals(TradeType.SINGLE_INVESTMENT) ||
                tradeType.equals(TradeType.REGULAR_INVESTMENT) ||
                tradeType.equals(TradeType.IRREGULAR_INVESTMENT)) {
            Account id = trade.getAccountByDaccountId();
            FundAccount account = id.getFundAccount();
            fund = account.getFund();
            /*
            * for the trade type of selling, change fund and switch investment,
            * we will estimate whether the source account(generally is fund account)
            * can be traded now or not, and we will omit the destination account estimation even though
            * the trade type of change fund and switch investment would be fund account.
            */
        } else if (tradeType.equals(TradeType.SELLING) ||
                tradeType.equals(TradeType.CHANGE_FUND) ||
                tradeType.equals(TradeType.SWITCH_INVESTMENT)) {
            Account id = trade.getAccountBySaccountId();
            FundAccount account = id.getFundAccount();
            fund = account.getFund();
        } else {
            log.error("Wrong trade type");
            throw new BizException(BizExceptionCode.TRADE_TYPE_NOT_EXIST,
                        "trade.tradetype.invalid", new Object[]{trade.getId()}, null);
        }
        FundType fundType = fund.getType();
        FundArea fundArea = fund.getArea();
        Long fundId = fund.getId();

        return !this.holidayDao.checkDay() && this.tradeTimeDao.checkTime(tradeType, fundType.getId(), fundArea.getId(), fundId);

    }

    /**
     * the main method of handling the dealings, it will automatically forward to the related
     * executors by judging the trade type
     *
     * @param tradeTask the dealing that will be handled.
     */
    public boolean executeTradeTask(TradeTask tradeTask) throws BizException {
        boolean result;
        Trade trade = tradeTask.getTrade();
        Integer tradeType = trade.getType();
        try {
            if (tradeType.equals(TradeType.SINGLE_INVESTMENT)) {
                result = handleSingleInvestment(tradeTask);
            } else if (tradeType.equals(TradeType.REGULAR_INVESTMENT)) {
                result = handleRegularInvestment(tradeTask);
            } else if (tradeType.equals(TradeType.IRREGULAR_INVESTMENT)) {
                result = handleIrregularInvestment(tradeTask);
            } else if (tradeType.equals(TradeType.SELLING)) {
                result = handleSelling(tradeTask);
            } else if (tradeType.equals(TradeType.CHANGE_FUND)) {
                result = handleChangeFund(tradeTask);
            } else if (tradeType.equals(TradeType.SWITCH_INVESTMENT)) {
                result = handleSwitchInvestment(tradeTask);
            } else {
                log.error("Wrong trade type");
                throw new BizException(BizExceptionCode.TRADE_TYPE_NOT_EXIST,
                        "trade.tradetype.invalid", new Object[]{trade.getId()}, null);
            }
        } catch (Exception e) {// when exception throws
            e.printStackTrace();
            log.error(e.getMessage());
            

            // continue throw out this exception
            throw new BizException(BizExceptionCode.TRADE_EXECUTE_ERROR, "trade.execute.error",
                    new Object[]{trade.getId()}, e.getCause());
        }
        return result;
    }

    /**
     * The main method for execute the single investment trade
     *
     * @param tradeTask the trade object that will be handling
     * @return boolean true - success; false - failed
     */
    synchronized boolean handleSingleInvestment(TradeTask tradeTask) throws BizException {
        Trade trade = tradeTask.getTrade();
        CashAccount freezeCashAccount = trade.getAccountBySaccountId().getCashAccount();
        FundAccount fundAccount = trade.getAccountByDaccountId().getFundAccount();
        checkTradeMode(trade, TradeMode.MONEY);
        // update the freeze cash account
        freezeCashAccount.setCount(freezeCashAccount.getCount().subtract(trade.getCount()).subtract(trade.getFee()));
        this.cashAccountDao.update(freezeCashAccount);

        // add the new bought fund quantity to fund account
        BigDecimal fundEquity = getFundEquity(fundAccount.getFund().getId());
        BigDecimal exchangeRate = getExchangeRate(fundAccount.getFund().getCurrency().getType(),
                trade.getCurrency().getType());
//        System.out.println("count = " + trade.getCount());
//        System.out.println("fundequity = " + fundEquity);
//        System.out.println("exchangeRate = " + exchangeRate);
        BigDecimal fundQuantity = trade.getCount().divide(fundEquity.multiply(exchangeRate), 10, BigDecimal.ROUND_HALF_UP);
//        System.out.println("fundquantity = " + fundQuantity);
        fundAccount.setCount(fundAccount.getCount().add(fundQuantity));
        fundAccount.setInitCount(fundAccount.getInitCount().add(trade.getCount()));
        this.fundAccountDao.update(fundAccount);

        // update trade status
        trade.setStatus(TradeStatus.EXECUTED);
        trade.setTradeTime(getSysdate());
        this.tradeDao.update(trade);

        // update trade task status
        updateTradeAndTask(tradeTask);
        return true;
	}

    /**
     * The main method for execute the regular investment
     *
     * @param tradeTask the trade object that will be handling
     * @return boolean true - success; false - failed
     */
    synchronized boolean handleRegularInvestment(TradeTask tradeTask) throws BizException {
        Trade trade = tradeTask.getTrade();
        CashAccount cashAccount = trade.getAccountBySaccountId().getCashAccount();
        FundAccount fundAccount = trade.getAccountByDaccountId().getFundAccount();
        checkTradeMode(trade, TradeMode.MONEY);

        // check the balance of user's cash account
        BigDecimal balance = cashAccount.getCount();
        BigDecimal payment = trade.getCount().add(trade.getFee());
        checkBalance(balance, payment);

        // update the cash account balance
        cashAccount.setCount(balance.subtract(payment));
        this.cashAccountDao.update(cashAccount);

        // add the new bought fund quantity to fund account
        BigDecimal fundEquity = getFundEquity(fundAccount.getFund().getId());
        BigDecimal exchangeRate = getExchangeRate(fundAccount.getFund().getCurrency().getType(),
                trade.getCurrency().getType());

        BigDecimal fundQuantity = trade.getCount().divide(fundEquity.multiply(exchangeRate), 10, BigDecimal.ROUND_HALF_UP);
        fundAccount.setInitCount(fundAccount.getInitCount().add(trade.getCount()));
        fundAccount.setCount(fundAccount.getCount().add(fundQuantity));
        this.fundAccountDao.update(fundAccount);

        // update trade status
        updateTradeAndTask(tradeTask);
        return true;
	}

    /**
     * The main method for execute the irregular investment
     *
     * @param tradeTask the trade object that will be handling
     * @return boolean true - success; false - failed
     */
    synchronized boolean handleIrregularInvestment(TradeTask tradeTask) throws BizException {
        return handleRegularInvestment(tradeTask);
	}

    /**
     * The main method for execute the selling fund
     *
     * @param tradeTask the trade object that will be handling
     * @return boolean true - success; false - failed
     */
    synchronized boolean handleSelling(TradeTask tradeTask) throws BizException {
        Trade trade = tradeTask.getTrade();
        // source account should be the fund account
        FundAccount freezeFundAccount = trade.getAccountBySaccountId().getFundAccount();
        // destination account should be cash account
        CashAccount cashAccount = trade.getAccountByDaccountId().getCashAccount();
        checkTradeMode(trade, TradeMode.UNIT);

//		 subtract the selling quantity of fund from freeze fund account
        freezeFundAccount.setCount(freezeFundAccount.getCount().subtract(trade.getCount()));
        this.fundAccountDao.update(freezeFundAccount);

        // update the frozen cash account balance
        BigDecimal fundEquity = getFundEquity(freezeFundAccount.getFund().getId());
        BigDecimal exchangeRate = getExchangeRate(freezeFundAccount.getFund().getCurrency().getType(),
                trade.getCurrency().getType());
        BigDecimal tradeAmount = trade.getCount().multiply(fundEquity).multiply(exchangeRate);
        BigDecimal handlingTariff = this.sysParamDao.getHandlingTariff(tradeAmount);
        trade.setFee(handlingTariff);
        cashAccount.setCount(cashAccount.getCount().add(tradeAmount).subtract(handlingTariff));
        this.cashAccountDao.update(cashAccount);
        
        // update the initial amount of the fund
        Long fundId = freezeFundAccount.getFund().getId();
        Set<Account> accounts = freezeFundAccount.getAccount().getUser().getAccounts();
        FundAccount fundAccount = findFundAccountByFundAndAccounts(fundId, accounts);
        if(fundAccount != null) {
        	fundAccount.setInitCount(fundAccount.getInitCount().subtract(tradeAmount));
        	this.fundAccountDao.update(fundAccount);
        }
//		check the saving plan. If all funds are selling, the saving plan will stop
        SavingPlan savingPlan = findSavingPlanByFundAndAccounts(fundId, accounts);
        if (savingPlan != null) {
            if (fundAccount != null && fundAccount.getCount().compareTo(BigDecimal.ZERO) <= 0) {
                savingPlan.setStatus(SavingPlanStatus.INVALID);
                savingPlan.setUpdateTime(getSysdate());
                this.savingPlanDao.update(savingPlan);
            }
        }

        // update trade status
        updateTradeAndTask(tradeTask);
        return true;
	}

    /**
     * The main method for execute the change fund
     *
     * @param tradeTask the trade object that will be handling
     * @return boolean true - success; false - failed
     */
    synchronized boolean handleChangeFund(TradeTask tradeTask) throws BizException {
        Trade trade = tradeTask.getTrade();
        // source account should be the fund account
        FundAccount freezeSourceFundAccount = trade.getAccountBySaccountId().getFundAccount();
        // destination account should also be fund account
        FundAccount destinationFundAccount = trade.getAccountByDaccountId().getFundAccount();
        checkTradeMode(trade, TradeMode.UNIT);

//		 subtract the selling quantity of fund from freeze source fund account
        freezeSourceFundAccount.setCount(freezeSourceFundAccount.getCount().subtract(trade.getCount()));
        this.fundAccountDao.update(freezeSourceFundAccount);

//		compute the value of the source fund
        BigDecimal sourceFundEquity = getFundEquity(freezeSourceFundAccount.getFund().getId());
        BigDecimal sourceFundExchangeRate = getExchangeRate(freezeSourceFundAccount.getFund().getCurrency().getType(),
                trade.getCurrency().getType());
        BigDecimal tradeAmount = trade.getCount().multiply(sourceFundEquity).multiply(sourceFundExchangeRate);

//		compute the fee of the trade
        BigDecimal transferTariff = this.sysParamDao.getTransferTariff(tradeAmount);
        trade.setFee(transferTariff);
        // update the initial count of source account
        Long sourceFundId = freezeSourceFundAccount.getFund().getId();
        Set<Account> accounts = freezeSourceFundAccount.getAccount().getUser().getAccounts();
        FundAccount sourceFundAccount = findFundAccountByFundAndAccounts(sourceFundId, accounts);
        sourceFundAccount.setInitCount(sourceFundAccount.getInitCount().subtract(tradeAmount));
        this.fundAccountDao.update(sourceFundAccount);

        // compute how many unit of new fund can be bought using these money
        BigDecimal destinationFundEquity = getFundEquity(destinationFundAccount.getFund().getId());
        BigDecimal destinationFundExchangeRate = getExchangeRate(destinationFundAccount.getFund().getCurrency().getType(),
                trade.getCurrency().getType());
        BigDecimal fundQuantity = tradeAmount.subtract(transferTariff).divide(destinationFundEquity.multiply(destinationFundExchangeRate), 10, BigDecimal.ROUND_HALF_UP);
        destinationFundAccount.setInitCount(destinationFundAccount.getInitCount().add(tradeAmount));
        destinationFundAccount.setCount(destinationFundAccount.getCount().add(fundQuantity));
        this.fundAccountDao.update(destinationFundAccount);

//		check the saving plan. If all funds are selling, the saving plan will stop
        Long destinationFundId = destinationFundAccount.getFund().getId();
        SavingPlan sourceFundSavingPlan = findSavingPlanByFundAndAccounts(sourceFundId, accounts);
        SavingPlan destinationFundSavingPlan = findSavingPlanByFundAndAccounts(destinationFundId, accounts);
        if (sourceFundAccount != null && sourceFundAccount.getCount().compareTo(BigDecimal.ZERO) == 0) {
            if (sourceFundSavingPlan != null) {
                sourceFundSavingPlan.setStatus(SavingPlanStatus.INVALID);
                sourceFundSavingPlan.setUpdateTime(getSysdate());
                this.savingPlanDao.update(sourceFundSavingPlan);
                if (destinationFundSavingPlan != null) {
                    destinationFundSavingPlan.setStatus(SavingPlanStatus.VALID);
                    destinationFundSavingPlan.setUpdateTime(getSysdate());
                    this.savingPlanDao.update(destinationFundSavingPlan);
                } else {
                    destinationFundSavingPlan = new SavingPlan();
                    destinationFundSavingPlan.setAccount(destinationFundAccount.getAccount());
                    destinationFundSavingPlan.setTradeType(sourceFundSavingPlan.getTradeType());
                    destinationFundSavingPlan.setCreateTime(getSysdate());
                    destinationFundSavingPlan.setFund(destinationFundAccount.getFund());
                    destinationFundSavingPlan.setCount(sourceFundSavingPlan.getCount());
                    destinationFundSavingPlan.setDay(sourceFundSavingPlan.getDay());
                    destinationFundSavingPlan.setIncomingSet(sourceFundSavingPlan.getIncomingSet());
                    destinationFundSavingPlan.setRate(sourceFundSavingPlan.getRate());
                    destinationFundSavingPlan.setStatus(SavingPlanStatus.VALID);
                    destinationFundSavingPlan.setUpdateTime(getSysdate());
                    this.savingPlanDao.create(destinationFundSavingPlan);
                }
            }
        } else if (sourceFundAccount != null && sourceFundAccount.getCount().compareTo(BigDecimal.ZERO) == 1) {
            if (destinationFundSavingPlan != null) {
                destinationFundSavingPlan.setStatus(SavingPlanStatus.INVALID);
                destinationFundSavingPlan.setUpdateTime(getSysdate());
                this.savingPlanDao.update(destinationFundSavingPlan);
            }
        }

        // update trade status
        updateTradeAndTask(tradeTask);
        return true;
	}

    /**
     * The main method for execute the change fund
     * @param tradeTask the trade object that will be handling
     * @return boolean true - success; false - failed
     */
    synchronized boolean handleSwitchInvestment(TradeTask tradeTask) throws BizException {
        Trade trade = tradeTask.getTrade();
        // source account should be the fund account
        FundAccount freezeSourceFundAccount = trade.getAccountBySaccountId().getFundAccount();
        // destination account should also be fund account
        FundAccount destinationFundAccount = trade.getAccountByDaccountId().getFundAccount();
        checkTradeMode(trade, TradeMode.UNIT);

//		 subtract the selling quantity of fund from freeze source fund account
        freezeSourceFundAccount.setCount(freezeSourceFundAccount.getCount().subtract(trade.getCount()));
        this.fundAccountDao.update(freezeSourceFundAccount);

//		compute the value of the source fund
        BigDecimal sourceFundEquity = getFundEquity(freezeSourceFundAccount.getFund().getId());
        BigDecimal sourceFundExchangeRate = getExchangeRate(freezeSourceFundAccount.getFund().getCurrency().getType(),
                trade.getCurrency().getType());
        BigDecimal tradeAmount = trade.getCount().multiply(sourceFundEquity).multiply(sourceFundExchangeRate);

//		compute the selling tariff of the trade
        BigDecimal handlingTariff = this.sysParamDao.getHandlingTariff(tradeAmount);

        BigDecimal boughtTariff = this.sysParamDao.getHandlingTariffFromMixed(tradeAmount.subtract(handlingTariff));
        trade.setFee(handlingTariff.add(boughtTariff));
        // update the initial count of source fund
        Long sourceFundId = freezeSourceFundAccount.getFund().getId();
		Set<Account> accounts = freezeSourceFundAccount.getAccount().getUser().getAccounts();
        FundAccount sourceFundAccount = findFundAccountByFundAndAccounts(sourceFundId, accounts);
        sourceFundAccount.setInitCount(sourceFundAccount.getInitCount().subtract(tradeAmount));
        this.fundAccountDao.update(sourceFundAccount);
        
        // compute how many unit of new fund can be bought using these money
        BigDecimal destinationFundEquity = getFundEquity(destinationFundAccount.getFund().getId());
        BigDecimal destinationFundExchangeRate = getExchangeRate(destinationFundAccount.getFund().getCurrency().getType(),
                trade.getCurrency().getType());
        BigDecimal fundQuantity = tradeAmount.subtract(handlingTariff).subtract(boughtTariff).divide(destinationFundEquity.multiply(destinationFundExchangeRate), 10, BigDecimal.ROUND_HALF_UP);
        destinationFundAccount.setInitCount(destinationFundAccount.getInitCount().add(tradeAmount).subtract(handlingTariff));
        destinationFundAccount.setCount(destinationFundAccount.getCount().add(fundQuantity));
        this.fundAccountDao.update(destinationFundAccount);

//		check the saving plan. If all funds are selling, the saving plan will stop
        
        Long destinationFundId = destinationFundAccount.getFund().getId();
		SavingPlan sourceFundSavingPlan = findSavingPlanByFundAndAccounts(sourceFundId, accounts);
        SavingPlan destinationFundSavingPlan = findSavingPlanByFundAndAccounts(destinationFundId, accounts);
        if (sourceFundAccount != null && sourceFundAccount.getCount().compareTo(BigDecimal.ZERO) == 0) {
            if (sourceFundSavingPlan != null) {
                sourceFundSavingPlan.setStatus(SavingPlanStatus.INVALID);
                sourceFundSavingPlan.setUpdateTime(getSysdate());
                this.savingPlanDao.update(sourceFundSavingPlan);
            }
//            After switch the investment, the new investment must be single investment
            if (destinationFundSavingPlan != null) {
                destinationFundSavingPlan.setStatus(SavingPlanStatus.INVALID);
                destinationFundSavingPlan.setUpdateTime(getSysdate());
                this.savingPlanDao.update(destinationFundSavingPlan);
            }
        } else if (sourceFundAccount != null && sourceFundAccount.getCount().compareTo(BigDecimal.ZERO) == 1) {
            if (destinationFundSavingPlan != null) {
                destinationFundSavingPlan.setStatus(SavingPlanStatus.INVALID);
                destinationFundSavingPlan.setUpdateTime(getSysdate());
                this.savingPlanDao.update(destinationFundSavingPlan);
            }
        }

        // update trade status
        updateTradeAndTask(tradeTask);
        return true;
    }

    private Date getSysdate() {
        return this.tradeDao.getSysdate();
    }

    private BigDecimal getFundEquity(Long fundId) throws BizException {
        BigDecimal fundEquity = this.fundEquityDao.getLatestEqutiy(fundId);
        if(fundEquity == null || fundEquity.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Failed to get the latest fund equity, date = " + getSysdate() + ", fund id = " + fundId);
            throw new BizException(BizExceptionCode.FUND_EQUITY_NOT_EXIST,
                    "trade.fundQuity.notExist", new Object[]{getSysdate(), fundId}, null);
        }
        return fundEquity;
    }

    private BigDecimal getExchangeRate(Integer sourceCurrencyType, Integer destinationCurrencyType) throws BizException {
        if(sourceCurrencyType.equals(destinationCurrencyType)) {
            return BigDecimal.ONE;
        }
        BigDecimal exchangeRate = this.exchangeRateDao.getLatestExchangeRate(sourceCurrencyType,
                destinationCurrencyType);
        if(exchangeRate == null || exchangeRate.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Failed to get the latest exchange rate, date = " + getSysdate() + ", source currency type = " + sourceCurrencyType
                        + ", destination currency type = " + destinationCurrencyType);
            throw new BizException(BizExceptionCode.EXCHANGERATE_NOT_EXIST,
                    "trade.exchangeRate.notExist", new Object[]{getSysdate(), sourceCurrencyType, destinationCurrencyType}, null);
        }
        return exchangeRate;
    }

    private void checkTradeMode(Trade trade, int expectedTradeMode) throws BizException {
        if (trade.getTradeMode() != expectedTradeMode) {
            log.error("The trade mode of single investment should be " + expectedTradeMode);
            throw new BizException(BizExceptionCode.TRADE_MODE_ERROR,
                    "trade.trademode.error", new Object[]{trade.getId()}, null);
        }
    }

    private void updateTradeAndTask(TradeTask tradeTask) {
        // update trade status
        Trade trade = tradeTask.getTrade();
        trade.setStatus(TradeStatus.EXECUTED);
        trade.setTradeTime(getSysdate());
        this.tradeDao.update(trade);

        // update trade task status
        tradeTask.setCount(tradeTask.getCount() + 1);
        tradeTask.setLastTime(getSysdate());
        tradeTask.setStatus(TradeTaskStatus.EXECUTED);
        this.tradeTaskDao.update(tradeTask);
    }

    private void checkBalance(BigDecimal balance, BigDecimal payment) throws BizException {
        if(balance.compareTo(payment) == -1) {
            log.error("Account balance insufficient");
            throw new BizException(BizExceptionCode.BALANCE_INSUFFICIENT,
                    "trade.balance.insufficient", new Object[]{}, null);
        }
    }

    private SavingPlan findSavingPlanByFundAndAccounts(Long fundId, Set<Account> accounts) {
        SavingPlan savingPlan = null;
        List<SavingPlan> savingPlans = this.savingPlanDao.getSavingPlansByFundId(fundId);
//		System.out.println("88888888888888888888888888888accounts.size()=" + accounts.size());
//		System.out.println("88888888888888888888888888888savingPlans.size()=" + savingPlans.size());
		for (SavingPlan tempSavingPlan : savingPlans) {
            for(Account tempAccount : accounts) {
                if(tempAccount.getId().equals(tempSavingPlan.getAccount().getId())) {
                    savingPlan = tempSavingPlan;
                    break;
                }
            }
        }
        return savingPlan;
    }

    private FundAccount findFundAccountByFundAndAccounts(Long fundId, Set<Account> accounts) {
        FundAccount fundAccount = null;
        for (Account account: accounts) {
            if(account.getType().equals(AccountType.NON_FROZEN_FUND)) {
                if (account.getFundAccount() != null && account.getFundAccount().getFund().getId().equals(fundId)) {
                    fundAccount = account.getFundAccount();
                    break;
                }
            }
        }
        return fundAccount;
    }
    
    public TradeTaskDao getTradeTaskDao() {
    	return this.tradeTaskDao;
    }
    
    public TradeDao getTradeDao() {
    	return this.tradeDao;
    }
}
