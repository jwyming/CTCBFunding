package com.eds.ctcb.biz.deal;

import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.common.SessionKey;
import com.eds.ctcb.constant.CurrencyType;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.CashAccount;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.db.FundAccount;
import com.eds.ctcb.db.FundCompany;
import com.eds.ctcb.db.FundType;
import com.eds.ctcb.db.User;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.exception.BizExceptionCode;
import com.eds.ctcb.util.DataUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InvestmentBizImpl extends BaseBiz implements InvestmentBiz {
	public List<FundType> getFundTypeList() {
		return this.fundTypeDao.getAllFundType();
	}
	
	public List<FundCompany> getFundCompanyList() {
		return this.fundCompanyDao.getAllFundCompany();
	}


  public Fund getFundById(Long fundId) {
		return this.fundDao.getFundById(fundId);
	}

  public Fund getFundByCode(String fundCode) {
		return this.fundDao.getFundByCode(fundCode);
	}

	public List<Account> getCustomerFundList(Long userId) {
		return this.accountDao.getNotFrozenFundAccountByUserId(userId);
		
	}

  public Currency getCurrencyById(Long currencyId) {
    return this.currencyDao.getCurrencyById(currencyId);
  }

  public Set<Currency> getCashAccountCurrencyList(Long userId) {
      return this.userDao.getCashAccountCurrencyList(userId);
  }

  public Date getSysdate() {
		return this.tradeDao.getSysdate();
	}

	public BigDecimal getNonFrozenFundAmount(String fundCode, Long userId) {
		return this.fundAccountDao.getNonFrozenFundAmount(fundCode,userId);
	}

	public Long getCompanyByFundCode(String fundCode) {
		return this.fundDao.getCompanyByFundCode(fundCode);
	}

	public BigDecimal getNonFrozenFundValue(String fundCode, Long userId) {
		return this.fundAccountDao.getNonFrozenFundValue(fundCode, userId);
	}
	
	public ActionForward preInvestment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			Long userId = this.getUser(request).getId();
			
			// get the currency list of this user's cash account
			Set<Currency> accountCurrencyList = new LinkedHashSet<Currency>();
			accountCurrencyList = this.getCashAccountCurrencyList(userId);
			HashMap<Long, String> accountCurrencyMap = new LinkedHashMap<Long, String>();
			for (Currency currency : accountCurrencyList) {
				accountCurrencyMap.put(currency.getId(), currency.getName());
			}
			request.getSession().setAttribute(SessionKey.MAP_ACCOUNT_CURRENCY,accountCurrencyMap);

			// set the fund list of this user's fund account
			List<Account> custFundAccountList = new ArrayList<Account>();
			custFundAccountList = this.getCustomerFundList(userId);
			HashMap<String, String> custFundMap = new LinkedHashMap<String, String>();
			for (Account account : custFundAccountList) {
				custFundMap.put(account.getFundAccount().getFund().getCode(), account.getFundAccount().getFund().getName());
			}
			request.getSession().setAttribute(SessionKey.MAP_CUST_FUND,custFundMap);
			
			//			get fundType list and set typeMap
			 List<FundType> fundTypeList = new ArrayList<FundType>();
			 fundTypeList = this.getFundTypeList();
			 HashMap<Long, String> fundTypeMap = new LinkedHashMap<Long, String>();
			 for(FundType fundType : fundTypeList){
				 fundTypeMap.put(fundType.getId(), fundType.getName());
			 }
			 request.getSession().setAttribute(SessionKey.MAP_FUND_TYPE, fundTypeMap);
			 
			 //get fundCompany list and set companyMap
			 List<FundCompany> fundCompanyList = new ArrayList<FundCompany>();
			 fundCompanyList = this.getFundCompanyList();		
			 HashMap<Long, String> fundCompanyMap = new LinkedHashMap<Long, String>();
			 for(FundCompany fundCompany : fundCompanyList){
				 fundCompanyMap.put(fundCompany.getId(), fundCompany.getName());
			 }
			request.getSession().setAttribute(SessionKey.MAP_FUND_COMPANY, fundCompanyMap);
			


		} catch (BizException e) {
			e.saveMessage(request, request.getContextPath()
					+ mapping.findForward("failureReturn").getPath());
			return mapping.findForward("failure");
		}
		return mapping.findForward("success");
	}
	public User getUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		LoginBean loginBean = (LoginBean) session
				.getAttribute(SessionKey.GLOBAL_LOGIN_INFO);
		Long userId = loginBean.getUserId();
		User user = this.userDao.getUserByID(userId);
		return user;
	}
	public boolean checkDate(String sdate){
		//String sdate = switchInvestmentForm.getInvestmentDate();
		Date ddate = DataUtil.str2Date(sdate, false);
		String snow = DataUtil.date2Str(this.getSysdate(), false);
		Date now = DataUtil.str2Date(snow, false);
		if (DataUtil.date2Int(ddate) < DataUtil.date2Int(now)) {
			//errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.select.date.error"));
			return false;
		}else{
			return true;
		}
	}
	public boolean checkFundCodeInput(String fundCode3,int fundIdRadio){
		boolean check = true;
		if (fundIdRadio == FundIdRadio.THREE && !DataUtil.isStrEqual(fundCode3, null)) {
            Fund fund = this.getFundByCode(fundCode3);
            if (fund == null) {
            	//errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("deal.input.fund.notExists"));
            	check = false;
            }
		}
		return check;
	}
	public FundAccount createFundAccount(int accountType,User user,Fund inFund,Float incomingSet){
		  Account newAccount = new Account();
          newAccount.setCreateTime(this.getSysdate());
          newAccount.setRemark(null);
          newAccount.setType(accountType);
          newAccount.setUser(user);
          this.createAccount(newAccount);
          // create fund account
          FundAccount newFundAccount = new FundAccount();
          newFundAccount.setAccount(newAccount);
          newFundAccount.setCount(BigDecimal.ZERO);
          newFundAccount.setFund(inFund);
          newFundAccount.setIncomingSet(incomingSet);
          newFundAccount.setInitCount(BigDecimal.ZERO);
          this.createFundAccount(newFundAccount);
          return newFundAccount;
	}
	public FundAccount getFundAccount(int accountType, Fund fund, User user ){
		Set<Account> accounts = user.getAccounts();
		FundAccount fundAccount = null;
        for(Account account : accounts){
        	if(account.getType().equals(accountType)){
        		if(account.getFundAccount().getFund().getId().equals(fund.getId())){
        			fundAccount = account.getFundAccount();
        		}
        	}
        }
        return fundAccount;
	}
	public CashAccount getCashAccount(int accountType,Long currencyId, User user){
		Set<Account> accounts = user.getAccounts();
		CashAccount cashAccount = null;
        for(Account account : accounts){
        	if(account.getType().equals(accountType)){
        		if(account.getCashAccount().getCurrency().getId().equals(currencyId)){
        			cashAccount = account.getCashAccount();
        		}
        	}
        }
        return cashAccount;
	}
	
	public void createAccount(Account newAccount){
        this.accountDao.create(newAccount);

	}

	public void createFundAccount(FundAccount newFundAccount) {
        this.fundAccountDao.create(newFundAccount);
	}

	public void createCashAccount(CashAccount newCashAccount) {
		this.cashAccountDao.create(newCashAccount);
	}

	public BigDecimal getFundEquity(Long inFundId) {
		// TODO Auto-generated method stub
		BigDecimal fundEquity = this.fundEquityDao.getLatestEqutiy(inFundId);
		if (fundEquity == null) {
			throw new BizException(BizExceptionCode.FUND_EQUITY_NOT_EXIST,
					"fundEquity.notFound", null, null);
		}
		return fundEquity;
		
	}

	public BigDecimal getExchangeRate(Integer sCurrencyType,Integer dCurrencyType) {
		// TODO Auto-generated method stub
		BigDecimal exchangeRate = this.exchangeRateDao.getLatestExchangeRate(sCurrencyType, dCurrencyType);
		if (exchangeRate == null) {
			throw new BizException(BizExceptionCode.EXCHANGERATE_NOT_EXIST,
					"exchangeRate.notExsit", null, null);
		}
		return exchangeRate;
		
	}

	public BigDecimal getHandlingTariff(BigDecimal tradeAmount) {
		// TODO Auto-generated method stub
		return this.sysParamDao.getHandlingTariff(tradeAmount);
	}

	public List<Fund> getFundListByCompany(Long fundCompanyId) {
		// TODO Auto-generated method stub
		return this.fundDao.getFundsByCompany(fundCompanyId);
	}

	public List<Fund> getFundListByType(Long fundTypeId) {
		// TODO Auto-generated method stub
		return this.fundDao.getFundsByType(fundTypeId);
	}
	public Long getFirstCurrencyId(User user){
		Set<Currency> accountCurrencyList = new LinkedHashSet<Currency>();
		accountCurrencyList = getCashAccountCurrencyList(user.getId());
		Long firstCurrencyId = 0L;
		for(Currency currency : accountCurrencyList){
			firstCurrencyId = currency.getId();
			break;
		}		
		return firstCurrencyId;
	}
}