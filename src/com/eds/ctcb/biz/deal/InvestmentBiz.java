package com.eds.ctcb.biz.deal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.CashAccount;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.db.FundAccount;
import com.eds.ctcb.db.FundCompany;
import com.eds.ctcb.db.FundType;
import com.eds.ctcb.db.User;

/**
 * The common class for deal module, many common methods used by deal module
 * put in this class.
 *
 * @author dz06tx
 */
public interface InvestmentBiz {
    /**
     * get the list of all fund type
     *
     * @return
     */
    public List<FundType> getFundTypeList();

    public List<FundCompany> getFundCompanyList();

    public List<Fund> getFundListByType(Long fundTypeId);

    public List<Fund> getFundListByCompany(Long fundCompanyId);

    public Fund getFundById(Long fundId);

    public Fund getFundByCode(String fundCode);

    public Long getCompanyByFundCode(String fundCode);


    public List getCustomerFundList(Long userId);

    public BigDecimal getNonFrozenFundAmount(String fundCode, Long userId);
    public BigDecimal getNonFrozenFundValue(String fundCode, Long userId);
    public Currency getCurrencyById(Long currencyId);

    public Set<Currency> getCashAccountCurrencyList(Long userId);

    public BigDecimal getHandlingTariff(BigDecimal tradeAmount);

    public Date getSysdate();
    
    public ActionForward preInvestment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response);
    public User getUser(HttpServletRequest request);
	public boolean checkDate(String sdate);
	public boolean checkFundCodeInput(String fundCode3,int fundIdRadio);
	public FundAccount createFundAccount(int accountType,User user,Fund inFund,Float incomingSet);
	public FundAccount getFundAccount(int accountType, Fund fund, User user );
	public CashAccount getCashAccount(int accountType,Long currencyId, User user);
	
	public void createAccount(Account newAccount);
	public void createFundAccount(FundAccount newFundAccount);
	public void createCashAccount(CashAccount newCashAccount);
	
	public BigDecimal getFundEquity(Long inFundId);
	
	public BigDecimal getExchangeRate(Integer sCurrencyType,Integer dCurrencyType);

	public Long getFirstCurrencyId(User user);
}
