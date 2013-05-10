package com.eds.ctcb.form.report;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.form.BaseForm;

public class InvestmentOrderForm extends BaseForm {

	private String userNickName;
	private String beginOrder;
	private String endOrder;
	/**
	 * 
	 */
	public InvestmentOrderForm() {
		
	}
	/**
	 * @param userNickName
	 * @param beginOrder
	 * @param endOrder
	 */
	
	
	public InvestmentOrderForm(String userNickName, String beginOrder, String endOrder) {

		this.userNickName = userNickName;
		this.beginOrder = beginOrder;
		this.endOrder = endOrder;
	}
	public String getBeginOrder() {
		return beginOrder;
	}
	public void setBeginOrder(String beginOrder) {
		this.beginOrder = beginOrder;
	}
	public String getEndOrder() {
		return endOrder;
	}
	public void setEndOrder(String endOrder) {
		this.endOrder = endOrder;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
	
		
		
        return null;
	}

}
