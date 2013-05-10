package com.eds.ctcb.action;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.eds.ctcb.bean.FundPerformanceEx;
import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.common.SessionKey;
import com.eds.ctcb.constant.CompetitionTopicType;
import com.eds.ctcb.constant.RoleType;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.FundArea;
import com.eds.ctcb.db.FundIndustry;
import com.eds.ctcb.db.FundType;
import com.eds.ctcb.db.ReportInfo;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.form.report.FundPerformanceForm;
import com.eds.ctcb.form.report.InvestmentOrderForm;
import com.eds.ctcb.util.DataUtil;
import com.eds.ctcb.util.I18NUtil;
import com.eds.ctcb.util.PageUtil;

public class ReportAction extends BaseAction {
	private final int PAGE_SIZE = 10;

	/**
	 * get all the information needed for custOverView.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	

	public ActionForward preCustOverView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			LoginBean loginfo = new LoginBean();
			loginfo = (LoginBean) request.getSession().getAttribute(
					SessionKey.GLOBAL_LOGIN_INFO);
			Long custId = loginfo.getUserId();

			Long userRole = loginfo.getRoleId();
			String userName = loginfo.getUserName();
		

			if (userRole.equals(RoleType.SYSTEM_ADM)) {

					return mapping.findForward("admin");
			}

			List<String> fundCodeList = this.reportBiz
					.getReachPerfectFund(custId);

			List<BigDecimal> custOverViewList = this.reportBiz
					.getCustOverView(custId);

			List custInvestmentList = this.reportBiz.getCustInvestment(custId);

			List custPreInvestmentList = this.reportBiz
					.getPreCustInvestment(custId);
			PageBean custPreInvestmentPageBean=this.reportBiz.getPreCustInvestmentInPage(custId, PAGE_SIZE, 1);

			
			Integer userOrder = this.reportBiz.getCustInvestOrder(custId);
			
			request.getSession().setAttribute("fundCodeList", fundCodeList);

			request.getSession().setAttribute("custOverViewList",
					custOverViewList);
			request.getSession().setAttribute("custInvestmentList",
					custInvestmentList);
			request.getSession().setAttribute("preInvestmentList",
					custPreInvestmentList);
			request.getSession().setAttribute("custPreInvestmentPageBean", custPreInvestmentPageBean);
			request.getSession().setAttribute("userOrder", userOrder);
			

		} catch (BizException e) {
			String jumpUrl = request.getContextPath()
					+ mapping.findForward("return").getPath();
			e.saveMessage(request, jumpUrl);

			return mapping.findForward("failure");

		}
		return mapping.findForward("success");
	}

	/**
	 * query information for custInvestment.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getCustInvestmentList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionMessages errors = new ActionMessages();
		try {
			InvestmentOrderForm investmentOrderForm;
			investmentOrderForm = (InvestmentOrderForm) form;
			
			if (investmentOrderForm != null&&DataUtil.isEmptyStr(investmentOrderForm.getUserNickName())&&investmentOrderForm.getBeginOrder()!= null&& investmentOrderForm.getEndOrder() != null) {
				
				if(DataUtil.isEmptyStr(investmentOrderForm.getBeginOrder())&& DataUtil.isEmptyStr(investmentOrderForm.getEndOrder())){
					
				}else if (investmentOrderForm.getBeginOrder().trim().equals("")|| investmentOrderForm.getEndOrder().trim().equals("")) {
					errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
							"report.custInvestment.order.spaceError"));
					this.saveErrors(request, errors);
					
					return mapping.getInputForward();
					
				} else {
                    Integer beginOrder=Integer.parseInt(investmentOrderForm.getBeginOrder());
					Integer endOrder = Integer.parseInt(investmentOrderForm
							.getEndOrder());
					Integer totalSize = this.reportBiz.getInvestmentOrderList()
							.size();
                   
                    if(beginOrder<=0){
                    	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
							"report.custInvestment.beginOrder.zero"));
                    }
                    if(beginOrder>endOrder){
                    	errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
						"report.custInvestment.order.reverse"));
                    }
					if (endOrder > totalSize) {

						errors.add(ActionErrors.GLOBAL_MESSAGE,
								new ActionMessage(
										"report.custInvestment.order.overRound",new Object[]{totalSize}));	
					}
					this.saveErrors(request, errors);
					if(!errors.isEmpty()){
					   return mapping.getInputForward();
					}
				}
			}

			List custInvestmentList = this.reportBiz
					.qryInvestmentOrderInList(investmentOrderForm);

			List reportInfoList = this.reportBiz.getLatest4ComptitionTopic();

			request.getSession().setAttribute("reportInfoList", reportInfoList);

			request.getSession().setAttribute("custInvestmentList",
					custInvestmentList);

			request.getSession().setAttribute("custInvestmentListPageBean",
					PageUtil.doPagenate(custInvestmentList, PAGE_SIZE, 1));

			request.getSession().setAttribute("investmentOrderForm",
					investmentOrderForm);
		} catch (BizException e) {
			e.saveMessage(request, request.getContextPath()
					+ mapping.findForward("return").getPath());

			return mapping.findForward("failure");
		}

		return mapping.findForward("success");
	}

	/**
	 * for paging in the custInvestment.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward pagingCustInvestmentList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		int page = 1;
		if (!DataUtil.isEmptyStr(request.getParameter("page"))) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		List fundPerformanceList = (List) (request.getSession()
				.getAttribute("custInvestmentList"));

		request.setAttribute("custInvestmentListPageBean", PageUtil.doPagenate(
				fundPerformanceList, PAGE_SIZE, page));

		return mapping.findForward("success");

	}

	/**
	 * query informaion for fundPerformance.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getFundPerformanceList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			// 1.init fundTypeMap
			HashMap<Long, String> fundTypeMap = new LinkedHashMap<Long, String>();
			List<FundType> fundTypeList = this.reportBiz.getFundType();
			Iterator it = fundTypeList.iterator();
			while (it.hasNext()) {
				FundType fundType = (FundType) it.next();
				fundTypeMap.put(fundType.getId(), fundType.getName());
			}
			request.getSession().setAttribute("fundTypeMap", fundTypeMap);
			// 2.init fundAreaMap
			HashMap<Long, String> fundAreaMap = new LinkedHashMap<Long, String>();
			List<FundArea> fundAreaList = this.reportBiz.getFundArea();
			Iterator it2 = fundAreaList.iterator();
			while (it2.hasNext()) {
				FundArea fundArea = (FundArea) it2.next();
				fundAreaMap.put(fundArea.getId(), fundArea.getName());
			}
			request.getSession().setAttribute("fundAreaMap", fundAreaMap);
			// 3.init fundIndustryMap
			HashMap<Long, String> fundIndustryMap = new LinkedHashMap<Long, String>();
			List<FundIndustry> fundIndustryList = this.reportBiz
					.getFundIndustry();
			Iterator it3 = fundIndustryList.iterator();
			while (it3.hasNext()) {
				FundIndustry fundIndustry = (FundIndustry) it3.next();
				fundIndustryMap.put(fundIndustry.getId(), fundIndustry
						.getName());
			}
			request.getSession().setAttribute("fundIndustryMap",
					fundIndustryMap);
			// 4.init timeRankMap
			HashMap<Integer, String> timeRankMap = new LinkedHashMap<Integer, String>();

			timeRankMap.put(7, I18NUtil.getResourceBundle(request).getString(
					"report.fundPerformance.timeRank.week"));
			timeRankMap.put(30, I18NUtil.getResourceBundle(request).getString(
					"report.fundPerformance.timeRank.month"));
			timeRankMap.put(90, I18NUtil.getResourceBundle(request).getString(
					"report.fundPerformance.timeRank.quarter"));
			timeRankMap.put(360, I18NUtil.getResourceBundle(request).getString(
					"report.fundPerformance.timeRank.year"));
			request.getSession().setAttribute("timeRankMap", timeRankMap);
			// 5. init indexRankMap
			HashMap<Integer, String> indexRankMap = new LinkedHashMap<Integer, String>();

			indexRankMap.put(10, I18NUtil.getResourceBundle(request).getString(
					"report.fundPerformance.indexRank.ten"));
			indexRankMap.put(20, I18NUtil.getResourceBundle(request).getString(
					"report.fundPerformance.indexRank.twenty"));
			indexRankMap.put(50, I18NUtil.getResourceBundle(request).getString(
					"report.fundPerformance.indexRank.fifty"));
			indexRankMap.put(100, I18NUtil.getResourceBundle(request)
					.getString("report.fundPerformance.indexRank.hundred"));
			request.getSession().setAttribute("indexRankMap", indexRankMap);
			// 6. init currencyMap
			HashMap<Long, String> currencyMap = new LinkedHashMap<Long, String>();
			List<Currency> currencyList = this.reportBiz.getCurrency();
			Iterator it4 = currencyList.iterator();
			while (it4.hasNext()) {
				Currency currency = (Currency) it4.next();

				currencyMap.put(currency.getId(), currency.getName());
			}
			request.getSession().setAttribute("currencyMap", currencyMap);
			FundPerformanceForm fundPerformanceForm;
			// 7.init fundPerformanceForm

			fundPerformanceForm = (FundPerformanceForm) form;
			if(form!=null&&DataUtil.isEmptyStr(fundPerformanceForm.getTimeRank())){
				
				fundPerformanceForm.setTimeRank("7");			
				
				
			}
          
           
			request.getSession().setAttribute(SessionKey.FORM_FUND_PERFORMANCE,
					fundPerformanceForm);

			List<FundPerformanceEx> fundPerformanceList = new LinkedList<FundPerformanceEx>();
			fundPerformanceList = this.reportBiz
					.qryFundPerformanceInList(fundPerformanceForm);

			request.getSession().setAttribute("fundPerformanceList",
					fundPerformanceList);
			request.getSession().setAttribute("resultSize",
					fundPerformanceList.size());
			StringBuffer startEquityDay = new StringBuffer();
			startEquityDay.append(I18NUtil.getResourceBundle(request)
					.getString("report.fundPerformance.historyEquity"));
			startEquityDay.append(this.reportBiz.getStartEuityDay());
			request.getSession().setAttribute("startEquityDay",
					startEquityDay.toString());
			StringBuffer endEquityDay = new StringBuffer();
			endEquityDay.append(I18NUtil.getResourceBundle(request).getString(
					"report.fundPerformance.newestEquity"));
			endEquityDay.append(this.reportBiz.getEndEquityDay());
			request.getSession().setAttribute("endEquityDay", endEquityDay);
			request.getSession().setAttribute("fundPerformancePageBean",
					PageUtil.doPagenate(fundPerformanceList, PAGE_SIZE, 1));
		} catch (BizException e) {

			e.saveMessage(request, request.getContextPath()
					+ mapping.findForward("return").getPath());

			return mapping.findForward("failure");

		}

		return mapping.findForward("success");

	}

	/**
	 * for paging in the fundPerformance.jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward pagingFundPerformanceList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		int page = 1;
		if (!DataUtil.isEmptyStr(request.getParameter("page"))) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		List fundPerformanceList = (List) (request.getSession()
				.getAttribute("fundPerformanceList"));

		request.setAttribute("fundPerformancePageBean", PageUtil.doPagenate(
				fundPerformanceList, PAGE_SIZE, page));

		return mapping.findForward("success");

	}

	public ActionForward getReportDataList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String reportId = request.getParameter("param");
		Long id = Long.parseLong(reportId);
		PageBean reportPageBean = this.reportBiz.qryReportDataInPageByTopic(id,
				PAGE_SIZE, 1);

		List<ReportInfo> reportInfoList = this.reportBiz
				.getLatest4ComptitionTopic();
		for (ReportInfo reportInfo : reportInfoList) {
			if (reportInfo.getId().equals(id)) {
				StringBuffer title = new StringBuffer();
				title.append(I18NUtil.getResourceBundle(request).getString(
						"competitionTopic.title"));
				title.append(reportInfo.getYear());
				title.append("-");
				title.append(reportInfo.getQuarter());
				if (id.toString().equals(CompetitionTopicType.PERFORMANCE_KING)) {
					title.append(I18NUtil.getResourceBundle(request).getString(
							"competitionTopic.performanceKing"));
				}
				if (id.toString().equals(
						CompetitionTopicType.ASSET_CONFIGURATION_KING)) {
					title.append(I18NUtil.getResourceBundle(request).getString(
							"competitionTopic.assetConfigurationKing"));
				}
				if (id.toString().equals(CompetitionTopicType.SAVING_PLAN_KING)) {
					title.append(I18NUtil.getResourceBundle(request).getString(
							"competitionTopic.savingKing"));
				}

				request.getSession().setAttribute("title", title.toString());
			}
		}

		request.getSession().setAttribute("reportPageBean", reportPageBean);

		return mapping.findForward("success");
	}

	public ActionForward pagingReportDataList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		int page = 1;
		if (request.getParameter("page") != null
				&& !"".equals(request.getParameter("page"))) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		String reportId = request.getParameter("param");
		Long id = Long.parseLong(reportId);
		PageBean reportPageBean = this.reportBiz.qryReportDataInPageByTopic(id,
				PAGE_SIZE, page);

		request.getSession().setAttribute("reportPageBean", reportPageBean);

		return mapping.findForward("success");
	}

	public ActionForward pagingPreInvestmentList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		int page = 1;
		if (request.getParameter("page") != null
				&& !"".equals(request.getParameter("page"))) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		LoginBean loginfo = new LoginBean();
		loginfo = (LoginBean) request.getSession().getAttribute(
				SessionKey.GLOBAL_LOGIN_INFO);
		Long custId = loginfo.getUserId();
	    PageBean custPreInvestmentPageBean=this.reportBiz.getPreCustInvestmentInPage(custId, PAGE_SIZE, page);

		request.getSession().setAttribute("custPreInvestmentPageBean", custPreInvestmentPageBean);

		return mapping.findForward("success");
	}
}
