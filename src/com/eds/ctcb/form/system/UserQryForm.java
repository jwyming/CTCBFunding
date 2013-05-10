package com.eds.ctcb.form.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.form.BaseForm;

public class UserQryForm extends BaseForm {
	
	private String userName;
	private String realName;
	private String sex;
	private String role;

	public UserQryForm() {
		
	}
	
	public UserQryForm(String userName, String realName, String sex, String role) {
		
		this.userName = userName;
		this.realName = realName;
		this.sex = sex;
		this.role = role;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String  sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
