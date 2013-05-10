package com.eds.ctcb.biz.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import com.eds.ctcb.bean.CustInvestmentBean;
import com.eds.ctcb.bean.FundPerformance;
import com.eds.ctcb.bean.FundPerformanceEx;
import com.eds.ctcb.bean.InvestmentOrder;
import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.bean.TradeBean;
import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.CompetitionTopicType;
import com.eds.ctcb.constant.CurrencyType;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.db.FundAccount;
import com.eds.ctcb.db.FundArea;
import com.eds.ctcb.db.FundIndustry;
import com.eds.ctcb.db.FundType;
import com.eds.ctcb.db.ReportData;
import com.eds.ctcb.db.ReportInfo;
import com.eds.ctcb.db.Trade;
import com.eds.ctcb.db.User;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.exception.BizExceptionCode;
import com.eds.ctcb.form.report.FundPerformanceForm;
import com.eds.ctcb.form.report.InvestmentOrderForm;
import com.eds.ctcb.util.DataUtil;
import com.eds.ctcb.util.I18NUtil;

public class ReportBizImpl extends BaseBiz implements ReportBiz {

	public List<ReportInfo> getLatest4ComptitionTopic() {
		List<ReportInfo> resultList = new LinkedList<ReportInfo>();
		List reportInfoList = this.reportInfoDao.getReportListOrderByTime();
		if (reportInfoList == null) {
			return null;
		} else if (reportInfoList.size() >= 5) {
			Iterator it = reportInfoList.iterator();
			int i = 0;
			while (it.hasNext() && i <= 3) {
				ReportInfo reportInfo = (ReportInfo) it.next();
				if (this.reportDataDao.getReportDataByReportId(
						reportInfo.getId()).size() != 0) {

					resultList.add(reportInfo);
					i++;
				}
			}
			return resultList;

		} else {

			Iterator it = reportInfoList.iterator();
			int i = 0;
			while (it.hasNext() && i < reportInfoList.size()) {
				ReportInfo reportInfo = (ReportInfo) it.next();
				if (this.reportDataDao.getReportDataByReportId(
						reportInfo.getId()).size() != 0) {

					resultList.add(reportInfo);
					i++;
				}
			}
			return resultList;
		}

	}

	private String startEuityDay = null;

	private String endEquityDay = null;

	/**
	 * return customer investments overview by customer id
	 * 
	 * @param custId
	 *            id of User
	 * 
	 */
	// 1.get all fundAccount
	// 3.get count
	// 4.get fund latest equity
	// 5.get fund latest exchageRate
	// 6.get init cash
	// 7.get fund current value
	// 8.get incoming
	// 9.get profit
	public List<CustInvestmentBean> getCustInvestment(Long custId) {
		List<CustInvestmentBean> resultList = new LinkedList<CustInvestmentBean>();
		String investmentCurrency=this.accountDao.getCashAccountCurrency(custId);
		List<Account> custAccountList = this.accountDao
				.getNotFrozenFundAccountByUserId(custId);
		Iterator custAccountIt = custAccountList.iterator();
		while (custAccountIt.hasNext()) {
			CustInvestmentBean custInvestmentBean = new CustInvestmentBean();

			Account account = (Account) custAccountIt.next();
			FundAccount fundAccount = account.getFundAccount();
			Fund dFund = fundAccount.getFund();

			custInvestmentBean.setFundCode(dFund.getCode());
			custInvestmentBean.setFundName(dFund.getName());
			custInvestmentBean.setInvestmentCurrency(investmentCurrency);

			custInvestmentBean.setCashCurrency(dFund.getCurrency().getName());
			custInvestmentBean.setTotalCount(fundAccount.getCount());

			BigDecimal count = account.getFundAccount().getCount();
			Integer currencyType = dFund.getCurrency().getType();

			BigDecimal fundEquity = this.fundEquityDao.getLatestEqutiy(dFund
					.getId());
			if (fundEquity == null) {
				throw new BizException(BizExceptionCode.FUND_EQUITY_NOT_EXIST,
						"fundEquity.notFound", null, null);
			}

			custInvestmentBean.setSellingEquity(fundEquity);

			BigDecimal exchangeRate = this.exchangeRateDao
					.getLatestExchangeRate(currencyType, CurrencyType.NTD);
			if (exchangeRate == null) {
				throw new BizException(BizExceptionCode.EXCHANGERATE_NOT_EXIST,
						"exchangeRate.notExsit", null, null);
			}

			custInvestmentBean.setSellingRate(exchangeRate);
			custInvestmentBean.setTotalInitCash(fundAccount.getInitCount());

			BigDecimal tempInvestmentAmount = count.multiply(fundEquity);
			

				tempInvestmentAmount = tempInvestmentAmount.multiply(exchangeRate);

		
			custInvestmentBean.setTotalFundValue(tempInvestmentAmount);
			custInvestmentBean.setCurrentComingSet(tempInvestmentAmount
					.subtract(fundAccount.getInitCount()));

			if (fundAccount.getInitCount() == BigDecimal.ZERO) {
				custInvestmentBean.setTotalProfit(BigDecimal.ZERO);
			} else {
				custInvestmentBean.setTotalProfit(tempInvestmentAmount
						.subtract(fundAccount.getInitCount()).divide(
								fundAccount.getInitCount(), 10,
								BigDecimal.ROUND_HALF_UP));
			}
			resultList.add(custInvestmentBean);
		}
		return resultList;
	}

	/**
	 * return: customer overview include cash and fund value. compute
	 * method:cash=count*exchangeRate(latest)
	 * fundAsset=count*equity(latest)*exchangeRate(latest)
	 * 
	 * @param custId
	 *            id of user
	 */
	public List<BigDecimal> getCustOverView(Long custId) {
		List<BigDecimal> resultList = new LinkedList<BigDecimal>();
		BigDecimal cashAmount = BigDecimal.ZERO;
		BigDecimal investmentAmount = BigDecimal.ZERO;
		List<Account> custAccountList = this.accountDao
				.getAllAccountByUserId(custId);
		Iterator custAccountIt = custAccountList.iterator();
		while (custAccountIt.hasNext()) {
			Account account = (Account) custAccountIt.next();
			// 1.compute cash
			if (account.getType() == AccountType.FROZEN_CASH
					|| account.getType() == AccountType.NON_FROZEN_CASH) {
				Integer currencyType = account.getCashAccount().getCurrency()
						.getType();

				BigDecimal tempCashAmount = account.getCashAccount().getCount();
				
					BigDecimal exchangeRate = this.exchangeRateDao
							.getLatestExchangeRate(currencyType,
									CurrencyType.NTD);
					if (exchangeRate == null) {
						throw new BizException(
								BizExceptionCode.EXCHANGERATE_NOT_EXIST,
								"exchangeRate.notExsit.index.jsp", null, null);
					}

					tempCashAmount = tempCashAmount.multiply(exchangeRate);

				cashAmount = cashAmount.add(tempCashAmount);
			}
			// 2.compute fundInvestment
		
	
			
			if (account.getType() == AccountType.FROZEN_FUND
					|| account.getType() == AccountType.NON_FROZEN_FUND) {
				
				Fund dFund = account.getFundAccount().getFund();
				BigDecimal count = account.getFundAccount().getCount();
				Integer currencyType = dFund.getCurrency().getType();

				BigDecimal fundEquity = this.fundEquityDao
						.getLatestEqutiy(dFund.getId());
				if (fundEquity == null) {
					throw new BizException(
							BizExceptionCode.FUND_EQUITY_NOT_EXIST,
							"fundEquity.notFound", null, null);
				}
				BigDecimal tempInvestmentAmount = count.multiply(fundEquity);
				
					BigDecimal exchangeRate = this.exchangeRateDao
							.getLatestExchangeRate(currencyType,
									CurrencyType.NTD);

					if (exchangeRate == null) {
						throw new BizException(
								BizExceptionCode.EXCHANGERATE_NOT_EXIST,
								"exchangeRate.notExsit", null, null);
					}
					tempInvestmentAmount = tempInvestmentAmount.multiply(exchangeRate);
				
				investmentAmount = investmentAmount.add(tempInvestmentAmount);
			}

		}
		resultList.add(cashAmount);
		resultList.add(investmentAmount);
		resultList.add(cashAmount.add(investmentAmount));
		return resultList;
	}

	/**
	 * return the invetments that not done of vustomer
	 * 
	 * @param custId
	 *            id of user
	 */
	public List getPreCustInvestment(Long custId) {

		return this.tradeDao.getNotExecTrade(custId);
	}
    public PageBean getPreCustInvestmentInPage(Long userId, int pageSize, int page){
    	PageBean tradePageBean=this.tradeDao.getNotExcuteTrade(userId, pageSize, page);
    	List<Trade> tradeList=tradePageBean.getList();
    	List<TradeBean> resultList=new LinkedList<TradeBean>();
    	if(tradeList==null){
    		return null;
    	}
    	Iterator it=tradeList.iterator();
    	while(it.hasNext())
    	{
    		Trade trade=(Trade)it.next();
    		TradeBean tradeBean=new TradeBean();
    		tradeBean.setId(trade.getId());
    		tradeBean.setType(trade.getType());
    		tradeBean.setTradeMode(trade.getTradeMode());
    		tradeBean.setSetTime(trade.getSetTime());
    		tradeBean.setCreateTime(trade.getCreateTime());
    		tradeBean.setCount(trade.getCount());
    		
    		Account sAccount=trade.getAccountBySaccountId();
    		Account dAccount=trade.getAccountByDaccountId();
    		if(sAccount.getType()==AccountType.FROZEN_CASH||sAccount.getType()==AccountType.NON_FROZEN_CASH){
    			
    			tradeBean.setAccountBySaccountId(I18NUtil.getResourceBundle(null).getString(
				"custOverView.jsp.customer.account"));
    			
    		}else{
    			tradeBean.setAccountBySaccountId(sAccount.getFundAccount().getFund().getName());
    			
    		}
    		
    		if(dAccount.getType()==AccountType.FROZEN_CASH||dAccount.getType()==AccountType.NON_FROZEN_CASH){
    			
    			tradeBean.setAccountByDaccountId(I18NUtil.getResourceBundle(null).getString(
				"custOverView.jsp.customer.account"));
    		}else{
    			tradeBean.setAccountByDaccountId(dAccount.getFundAccount().getFund().getName());
    			
    		}
    		resultList.add(tradeBean);
    	}
    	
    	tradePageBean.setList(resultList);
    	//return this.tradeDao.getNotExcuteTrade(userId, pageSize, page);
    	return tradePageBean;
    }
	/**
	 * resulst:return the fundcode when the fund reach the inComingSet,if the
	 * equity is not exsting on current day, then will find the latest day.
	 * condition:the fundEquity exist on the FundAccount createTime day.
	 * 
	 * 
	 * @param custId
	 *            id of user
	 */
	// 1.get all the fund
	// 2.compute investment incomingSet
	// method:(count*equity-initCount)/initCount
	// 3.return results
	public List<String> getReachPerfectFund(Long custId) {

		List<Account> custAccountList = this.accountDao
				.getNotFrozenFundAccountByUserId(custId);
		if (custAccountList == null) {
			return null;
		}
		Iterator it = custAccountList.iterator();
		List<String> resultIdList = new LinkedList<String>();

		while (it.hasNext()) {
			Account account = (Account) it.next();
			FundAccount fundAccount = account.getFundAccount();
			BigDecimal count = fundAccount.getCount();
			BigDecimal initCount = fundAccount.getInitCount();
			Fund fund = fundAccount.getFund();
			Long fundId = fund.getId();
			BigDecimal endEquity = this.fundEquityDao.getLatestEqutiy(fundId);
			BigDecimal exchangeRate=this.exchangeRateDao.getLatestExchangeRate(fund.getCurrency().getType(), CurrencyType.NTD);
			if(exchangeRate==null){
				throw new BizException(BizExceptionCode.EXCHANGERATE_NOT_EXIST,
						"exchangeRate.notExsit", null, null);
			}
			if (endEquity == null) {
				throw new BizException(BizExceptionCode.FUND_EQUITY_NOT_EXIST,
						"fundEquity.notFound", null, null);
			}
			count = count.multiply(endEquity).multiply(exchangeRate);
			Float initIncomingSet = fundAccount.getIncomingSet();
			
			
			if(!initIncomingSet.equals(0f)){
				
					BigDecimal preIncomingSet=new BigDecimal(initIncomingSet);
					BigDecimal tempIncomingSet=preIncomingSet.movePointLeft(2);
					tempIncomingSet.setScale(2, BigDecimal.ROUND_HALF_DOWN);
					initIncomingSet=tempIncomingSet.floatValue();
					
					Float realIncomingSet = 0f;
						if (initCount != BigDecimal.ZERO) {			
							realIncomingSet = (count.subtract(initCount)).divide(initCount,10, BigDecimal.ROUND_HALF_UP).floatValue();			
							  
						}
					// when reach perfect,add to the list
						if (realIncomingSet >= initIncomingSet) {
							resultIdList.add(fund.getName()+","+DataUtil.Float2Percent(initIncomingSet));
							
						}			
			}
		}
		return resultIdList;
	}

	public List<FundType> getFundType() {

		return this.fundTypeDao.getAllFundType();
	}

	public List<FundArea> getFundArea() {

		return this.fundAreaDao.getFundArea();
	}

	public List<FundIndustry> getFundIndustry() {

		return this.fundIndustryDao.getAllFundIndustry();
	}

	public List<Currency> getCurrency() {

		return this.currencyDao.getAllCurrency();
	}

	private QryBean fundPerformanceForm2QryBean(FundPerformanceForm form) {
		StringBuffer hqlBuffer = new StringBuffer();
		hqlBuffer.append("from Fund as t where 1=1");
		List<Object> paramList = new ArrayList<Object>();

		// add conditions
		if (form != null) {
			if (form.getFundType() != null
					&& form.getFundType().trim().length() > 0) {
				hqlBuffer.append(" and t.type.id=? ");
				paramList.add(new Integer(form.getFundType()));
			}

			if (form.getFundArea() != null
					&& form.getFundArea().trim().length() > 0) {
				hqlBuffer.append(" and  t.area.id=? ");
				paramList.add(new Integer(form.getFundArea()));
			}

			if (form.getFundIndustry() != null
					&& form.getFundIndustry().trim().length() > 0) {
				hqlBuffer.append(" and t.industry.id=? ");
				paramList.add(new Integer(form.getFundIndustry()));
			}

			if (form.getCurrency() != null
					&& form.getCurrency().trim().length() > 0) {
				hqlBuffer.append(" and t.currency.id=? ");
				paramList.add(new Integer(form.getCurrency()));
			}

		}
		String hql = hqlBuffer.toString();
	
		Object[] paramArray = paramList.toArray();
		return new QryBean(hql, paramArray);
	}
	

	// not used method: see qryFundPerformanceInList()
	public PageBean qryFundPerformanceInPage(FundPerformanceForm form,
			int pageSize, int pageNumber) {

		QryBean qryBean = fundPerformanceForm2QryBean(form);

		PageBean tmp = this.fundDao.qryFundPerformanceInPage(qryBean, pageSize,
				pageNumber);

		return tmp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eds.ctcb.biz.report.ReportBiz#qryInvestmentOrderInList(com.eds.ctcb.form.report.CustInvestmentForm)
	 */

	public List<InvestmentOrder> qryInvestmentOrderInList(
			InvestmentOrderForm form) {
		List<InvestmentOrder> initList = this.getInvestmentOrderList();
		List<InvestmentOrder> resultList = new LinkedList<InvestmentOrder>();
		Iterator it = initList.iterator();
		int listSize = initList.size();
		if (form != null) {
			String userNickName = form.getUserNickName();
			int beginOrder;
			int endOrder;
			if (form.getBeginOrder() != null && form.getEndOrder() != null
					&& !form.getBeginOrder().trim().equals("")
					&& !form.getEndOrder().trim().equals("")) {
				beginOrder = Integer.parseInt(form.getBeginOrder());
				endOrder = Integer.parseInt(form.getEndOrder());
			} else {
				beginOrder = 0;
				endOrder = 0;
			}

			if ((userNickName == null && form.getBeginOrder() == null && form
					.getEndOrder() == null)
					|| (userNickName.trim().equals("")
							&& form.getBeginOrder().equals("") && form
							.getEndOrder().equals(""))) {
				resultList = initList;
				return resultList;
			}
			if (userNickName != null && !userNickName.trim().equals("")) {

				while (it.hasNext()) {
					InvestmentOrder investmentOrder = (InvestmentOrder) it
							.next();
					if (investmentOrder.getUserNickName().contains(userNickName)) {
						resultList.add(investmentOrder);
						

					}
				}
				return resultList;

			} else if (beginOrder >= 0 && endOrder >= beginOrder
					&& endOrder <= listSize) {
				int i = 1;
				while (it.hasNext()) {
					InvestmentOrder investmentOrder = (InvestmentOrder) it
							.next();
					if (beginOrder <= i && endOrder >= i) {
						resultList.add(investmentOrder);
					}
					i++;
				}

			} else {
				resultList = this.getInvestmentOrderList();
			}
		} else {
			resultList = this.getInvestmentOrderList();
		}
		return resultList;
	}

	/**
	 * return the realtime order of user investment value
	 * 
	 * @param custId
	 *            id of user
	 */
	// 1.get all customer total asset
	// 2.sorting
	// 3.get user's rank
	public Integer getCustInvestOrder(Long custId) {
		List<InvestmentOrder> list = this.getInvestmentOrderList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			InvestmentOrder investmentOrder = (InvestmentOrder) it.next();
			if (investmentOrder.getUserNickName().equals(
					((User) this.userDao.findById(User.class, custId))
							.getUserName())) {
				return investmentOrder.getOrder();
			}
		}

		return null;
	}

	// 1.get all customer
	// 2.compute all customer's investment performance
	public List<InvestmentOrder> getInvestmentOrderList() {
		Set<InvestmentOrder> investmentOrderSet = new TreeSet<InvestmentOrder>();
		List<InvestmentOrder> resultList = new LinkedList<InvestmentOrder>();
		List<User> allUserList = this.userDao.getAllNotDeletedUsualUserList();
		Iterator userListIt = allUserList.iterator();
		while (userListIt.hasNext()) {
			InvestmentOrder investmentOrder = new InvestmentOrder();
			User user = (User) userListIt.next();
			investmentOrder.setUserNickName(user.getUserName());

			Long userId = user.getId();
			
			
		
		      	  List<BigDecimal> userOverView = this.getCustOverView(userId);
			      investmentOrder.setCashAsset(userOverView.get(0));
			      investmentOrder.setInvestmentAsset(this.sysParamDao
					.getInitInvAmount().subtract(userOverView.get(0)));
			      investmentOrder.setCurrentInvestmentAsset(userOverView.get(1));
			      investmentOrder.setTotalAsset(userOverView.get(2));
			      investmentOrderSet.add(investmentOrder);
		  

		}
		Object[] sortedArray = investmentOrderSet.toArray();
		int arraySize = sortedArray.length;
		ArrayList<InvestmentOrder> resultArray = new ArrayList<InvestmentOrder>(
				arraySize);
		for (int i = 1; i <= arraySize; i++) {
			InvestmentOrder tempInvestmentOrder = (InvestmentOrder) sortedArray[arraySize
					- i];
			tempInvestmentOrder.setOrder(i);
			resultArray.add(tempInvestmentOrder);
		}
		ListIterator resultListIt = resultArray.listIterator();

		while (resultListIt.hasNext()) {
			resultList.add((InvestmentOrder) resultListIt.next());
		}

		return resultList;
	}

	// get all fund performance into a list
	public List<FundPerformanceEx> qryFundPerformanceInList(
			FundPerformanceForm form) {
		QryBean qryBean = fundPerformanceForm2QryBean(form);
		// get all fund with the provided conditions
		List fundList = this.fundDao.qryInList(qryBean);

		// init the default timeRank and indexRank
		int indexRank = 0;
		int timeRank = 0;
		if (form != null) {
			if (form.getTimeRank() != null
					&& form.getTimeRank().trim().length() > 0) {
				timeRank = new Integer(form.getTimeRank());
			} else {
				timeRank = 7;
			}
			if (form.getIndexRank() != null
					&& form.getIndexRank().trim().length() > 0) {
				indexRank = new Integer(form.getIndexRank());
			} else {
				indexRank = 0;
			}
		} else {
			timeRank = 7;
			indexRank = 0;
		}

		Set<FundPerformance> performanceSet = new TreeSet<FundPerformance>();

		Iterator fundIt = fundList.iterator();
		while (fundIt.hasNext()) {
			Fund fund = (Fund) fundIt.next();

			List equityAndDateList = this.fundEquityDao
					.getFundEquityTodayAndOld(fund.getId(), timeRank);
			
			if (equityAndDateList==null||equityAndDateList.size()==0) {
				throw new BizException(BizExceptionCode.FUND_EQUITY_NOT_EXIST,
						"fundEquity.notFound", null, null);
			}
			BigDecimal testTodayEquity = ((BigDecimal[]) equityAndDateList
					.get(0))[0];
			BigDecimal testOldDayEquity = ((BigDecimal[]) equityAndDateList
					.get(0))[1];
			Date testToday = ((Date[]) equityAndDateList.get(1))[0];
			Date testOldDay = ((Date[]) equityAndDateList.get(1))[1];
//			SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd");
//
//			this.startEuityDay = dff.format(testOldDay);
//			this.endEquityDay = dff.format(testToday);
			this.startEuityDay=DataUtil.date2Str(testOldDay, false);
			this.endEquityDay=DataUtil.date2Str(testToday, false);

			BigDecimal performanceValue = computeFundPerformance(
					testOldDayEquity, testTodayEquity);
			FundPerformance fundPerformance = new FundPerformance(fund
					.getName(), fund.getCode(), fund.getType().getName(), fund
					.getCompany().getName(), fund.getArea().getName(), fund
					.getIndustry().getName(), testOldDayEquity,
					testTodayEquity, fund.getCurrency().getName(),
					performanceValue);
			performanceSet.add(fundPerformance);

		}

		Object[] sortedArray = performanceSet.toArray();
		ArrayList<FundPerformance> resultArray = new ArrayList<FundPerformance>(
				indexRank);
		int count = sortedArray.length;
		
         if(indexRank==0){
        	 indexRank=count;
         }
		if (count > indexRank) {
			for (int i = 1; i <= indexRank; i++) {
				resultArray.add((FundPerformance) sortedArray[count - i]);
			}
		} else {
			for (int i = 1; i <= count; i++) {
				resultArray.add((FundPerformance) sortedArray[count - i]);
			}
		}

		ListIterator resultListIt = resultArray.listIterator();

		List<FundPerformanceEx> resultList = new LinkedList<FundPerformanceEx>();
		Integer order = 1;
		while (resultListIt.hasNext()) {
			FundPerformanceEx tmp = new FundPerformanceEx(order,
					(FundPerformance) resultListIt.next());
			order++;
			resultList.add(tmp);
		}

		return resultList;
	}

	// compute the performance
	private BigDecimal computeFundPerformance(BigDecimal oldEquity,
			BigDecimal todayEquity) {
		if (oldEquity == BigDecimal.ZERO) {
			return BigDecimal.ZERO;
		} else {
			BigDecimal result = (todayEquity.subtract(oldEquity)).divide(
					oldEquity, 10, BigDecimal.ROUND_HALF_UP);
			return result;
		}
	}

	public String getEndEquityDay() {
		return endEquityDay;
	}

	private void setEndEquityDay(String endEquityDay) {
		this.endEquityDay = endEquityDay;
	}

	public String getStartEuityDay() {
		return startEuityDay;
	}

	private void setStartEuityDay(String startEuityDay) {
		this.startEuityDay = startEuityDay;
	}

	// method to qry reportData
	public PageBean qryReportDataInPageByTopic(Long reportId, int pageSize,
			int page) {

		return this.reportDataDao.qryReportDataInPageByTopic(reportId,
				pageSize, page);
	}

	/*
	 * 1.get the topic of special year and quarter,if is not exist,throw
	 * BizException 2.catelog 3 types competition topic:
	 * PERFORMANCE_KING,ASSET_CONFIGURATION_KING,SAVING_PLAN_KING
	 * 3.PERFORMANCE_KING: compute all customer's overView ,get his total asset.
	 * get the reportInfo id of the last quarter,if is not exist return null.
	 * use the reportId, get the user's last quarter total asset; if reportId is
	 * null, make last quarter total asset to initCount. compute his intrest,and
	 * incoming and incomingSet save to dadabase. select where user's
	 * incomgingSet>10% results sorted by desc by incoming set. and update rank
	 * column value. 4.
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.eds.ctcb.biz.report.ReportBiz#makeReportData()
	 */
	public synchronized Boolean makeReportData() {
     try{
		Calendar now = Calendar.getInstance();
		now.setTime(this.userDao.getSysdate());
	
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		int quarter = 0;
		if (month >= 1 && month <= 3) {
			quarter = 1;
		} else if (month >= 4 && month <= 6) {
			quarter = 2;
		} else if (month >= 7 && month <= 9) {
			quarter = 3;
		} else if (month >= 10 && month <= 12) {
			quarter = 4;
		}

		ReportInfo reportInfoThisQuarter = this.reportInfoDao
				.getReportInfoByFullCondition(year, quarter);
		if (reportInfoThisQuarter == null) {
			throw new BizException(
					BizExceptionCode.COMPETITION_TOPIC_NOT_EXIST,
					"can not find topic", null, null);
		}
		String topic = reportInfoThisQuarter.getTopic();

		Long reportId = reportInfoThisQuarter.getId();

		
		List<User> allUserList = this.userDao.getAllNotDeletedUsualUserList();
		
		Iterator userListIt = allUserList.iterator();
		
		while (userListIt.hasNext()) {
			
			
			ReportData tempReportData = new ReportData();
			User user = (User) userListIt.next();
			Long userId = user.getId();
			List<BigDecimal> userOverView = this.getCustOverView(userId);
			BigDecimal currentQuarterAsset = userOverView.get(2);
			BigDecimal lastQuarterAsset = BigDecimal.ZERO;
			ReportInfo lastReportInfo = this.reportInfoDao
					.getLsatReportInfoByFullCondition(year, quarter);
			
			if (lastReportInfo == null) {
				lastQuarterAsset = this.sysParamDao.getInitInvAmount();
				
			} else {
				ReportData lastReportData = this.reportDataDao
						.getCustReportDataByReportId(lastReportInfo.getId(),
								userId);
				lastQuarterAsset = lastReportData.getEndEquity();
			}
			
			tempReportData.setReportInfo(reportInfoThisQuarter);
			tempReportData.setRank(0);
			tempReportData.setUser(user);
			tempReportData.setStartEquity(lastQuarterAsset);
			tempReportData.setEndEquity(currentQuarterAsset);
			tempReportData.setIncoming(currentQuarterAsset
					.subtract(lastQuarterAsset));
			tempReportData.setIncomingRate(currentQuarterAsset.subtract(
					lastQuarterAsset).divide(lastQuarterAsset, 10,
					BigDecimal.ROUND_HALF_UP).floatValue());
			
			this.reportDataDao.create(tempReportData);
		}
		
		
		List<ReportData> resultList = new LinkedList<ReportData>();
		// condition:incomingSet>=10%
		if (topic.equals(CompetitionTopicType.PERFORMANCE_KING)) {

			resultList = this.reportDataDao
					.getPerformanceKingReportData(reportId);
		     
			int rank = 1;
			Iterator it = resultList.iterator();
			while (it.hasNext()) {
				ReportData notRankReportData = (ReportData) it.next();
				notRankReportData.setRank(rank);
				this.reportDataDao.update(notRankReportData);
				rank++;
			}

			return true;

		}
		// condition:fund types>4
		if (topic.equals(CompetitionTopicType.ASSET_CONFIGURATION_KING)) {
			resultList = this.reportDataDao
					.getAssetConfigKingKingReportData(reportId);
			Set<ReportData> investmentOrderSet = new TreeSet<ReportData>();
			
			for(int i=0;i<resultList.size();i++)
			{ 
				investmentOrderSet.add(resultList.get(i));
			}
			
			Object[] sortedArray = investmentOrderSet.toArray();
			int arraySize = sortedArray.length;
			ArrayList<ReportData> resultArray = new ArrayList<ReportData>(
					arraySize);
			for (int i = 1; i <= arraySize; i++) {
				ReportData tempReportData = (ReportData) sortedArray[arraySize
						- i];
			
				resultArray.add(tempReportData);
			}
			ListIterator it = resultArray.listIterator();
			int rank = 1;
			
			while (it.hasNext()) {
				ReportData notRankReportData = (ReportData) it.next();
				
				notRankReportData.setRank(rank);
				this.reportDataDao.update(notRankReportData);
				rank++;
			}
			
			return true;
		}
		// condition:user has saving_plan investment
		if (topic.equals(CompetitionTopicType.SAVING_PLAN_KING)) {
			resultList = this.reportDataDao
					.getSavingPlanKingReportData(reportId);
			int rank = 1;
			Iterator it = resultList.iterator();
			while (it.hasNext()) {
				ReportData notRankReportData = (ReportData) it.next();
				notRankReportData.setRank(rank);
				this.reportDataDao.update(notRankReportData);
				rank++;
			}
		
			return true;

		}
	
		return false;
	}catch(Exception e){
		
		return false;
	}
	}

}
