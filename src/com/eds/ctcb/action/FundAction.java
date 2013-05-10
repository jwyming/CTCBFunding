package com.eds.ctcb.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.common.SessionKey;
import com.eds.ctcb.db.Fund;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.util.DataUtil;

public class FundAction extends BaseAction {
	public ActionForward getFundsByType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String fundTypeId = request.getParameter("param");
		try {
			HashMap<String, String> fund1Map = new LinkedHashMap<String, String>();
			if (!DataUtil.isEmptyStr(fundTypeId)) {
				List<Fund> funds = this.investmentBiz.getFundListByType(Long
						.valueOf(fundTypeId));
				for (Fund fund : funds) {
					fund1Map.put(fund.getCode(), fund.getName());
					
				}
			}
			request.getSession().setAttribute(SessionKey.MAP_FUND1, fund1Map);
		} catch (BizException e) {
			log.error("error occurred when getting fund list by fund type");
		}
		return mapping.findForward("success");
	}

	public ActionForward getFundsByCompany(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String fundCompanyId = request.getParameter("param");
		try {
			HashMap<String, String> fund2Map = new LinkedHashMap<String, String>();
			if (!DataUtil.isEmptyStr(fundCompanyId)) {
				List<Fund> funds = this.investmentBiz.getFundListByCompany(Long
						.valueOf(fundCompanyId));
				for (Fund fund : funds) {
					fund2Map.put(fund.getCode(), fund.getName());
				}
			}
			request.getSession().setAttribute(SessionKey.MAP_FUND2, fund2Map);
		} catch (BizException e) {
			log.error("error occurred when getting fund list by fund company");
		}
		return mapping.findForward("success");

	}

	
	public ActionForward getCompanyFundsByFundCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String fundCode = request.getParameter("param");
		try {
			HashMap<String, String> fund3Map = new LinkedHashMap<String, String>();
			if (!DataUtil.isEmptyStr(fundCode)) {
				Long companyId = this.investmentBiz
						.getCompanyByFundCode(fundCode);
				List<Fund> funds = this.investmentBiz
						.getFundListByCompany(companyId);
				for (Fund fund : funds) {
					fund3Map.put(fund.getCode(), fund.getName());
				}
			}
			request.getSession().setAttribute(SessionKey.MAP_FUND3, fund3Map);
		} catch (BizException e) {
			log.error("error occurred when getting fund list by fund company");
		}
		return mapping.findForward("success");

	}

	
	public ActionForward computeHandlingTariff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String tradeAmount = request.getParameter("param");
		BigDecimal handlingTariff = null;
		try {
			if (!DataUtil.isEmptyStr(tradeAmount)) {
				handlingTariff = this.investmentBiz.getHandlingTariff(new BigDecimal(tradeAmount));
			}
			request.getSession().setAttribute("handlingTariff", handlingTariff);
		} catch  (BizException e) {
			log.error("error occurred when computing handlingTariff");
		} catch (Exception e1) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(e1
					.getMessage()));
			this.saveErrors(request, errors);			
		}
		return mapping.findForward("success");
	}

	public ActionForward getSumAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String inFundCode = request.getParameter("param");
		BigDecimal sumAmount = new BigDecimal(0);
		HttpSession session = request.getSession();
		LoginBean loginBean = (LoginBean) session
				.getAttribute(SessionKey.GLOBAL_LOGIN_INFO);
		Long userId = loginBean.getUserId();
		try {
			if (!DataUtil.isEmptyStr(inFundCode)) {
				sumAmount = this.investmentBiz.getNonFrozenFundAmount(inFundCode, userId);
			}

		request.getSession().setAttribute("sumAmount", sumAmount);


	} catch  (BizException e) {
		log.error("error occurred when computing handlingTariff");
		} catch (Exception e1) {
			ActionMessages errors = new ActionMessages();
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(e1
					.getMessage()));
			this.saveErrors(request, errors);
			
		}
		return mapping.findForward("success");
	}
}
