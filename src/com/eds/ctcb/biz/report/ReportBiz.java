package com.eds.ctcb.biz.report;

import java.math.BigDecimal;
import java.util.List;

import com.eds.ctcb.bean.FundPerformanceEx;
import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.FundArea;
import com.eds.ctcb.db.FundIndustry;
import com.eds.ctcb.db.FundType;
import com.eds.ctcb.db.ReportInfo;
import com.eds.ctcb.form.report.FundPerformanceForm;
import com.eds.ctcb.form.report.InvestmentOrderForm;

public interface ReportBiz {
	public List<String> getReachPerfectFund(Long custId);

	public List<BigDecimal> getCustOverView(Long custId);

	/*
	 * return customer investments overview by customer id
	 */
	public List getCustInvestment(Long custId);

	/*
	 * return customer non dealed investments by customer id
	 */
	public List getPreCustInvestment(Long custId);
	
	public PageBean getPreCustInvestmentInPage(Long userId, int pageSize, int page);

	public Integer getCustInvestOrder(Long custId);

	public List getInvestmentOrderList();

	public List qryInvestmentOrderInList(InvestmentOrderForm form);

	public PageBean qryFundPerformanceInPage(FundPerformanceForm form,int pageSize, int page);

	public List<FundPerformanceEx> qryFundPerformanceInList(FundPerformanceForm form);

	public List<FundType> getFundType();

	public List<FundArea> getFundArea();

	public List<FundIndustry> getFundIndustry();

	public List<Currency> getCurrency();

	public String getEndEquityDay();

	public String getStartEuityDay();

	public PageBean qryReportDataInPageByTopic(Long reportId, int pageSize,	int page);

	public Boolean makeReportData();

	public List<ReportInfo> getLatest4ComptitionTopic();

}
