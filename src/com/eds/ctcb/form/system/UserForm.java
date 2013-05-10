package com.eds.ctcb.form.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.form.BaseForm;

public class UserForm extends BaseForm {
	
	private String userName;
	private String password;
	private String realName;
	private String sex;
	private String role;
	private String company;
	private String address;
	private String phone;
	private String email;
	private String currency;
	
	
	public UserForm() {
	
	}
	
	public UserForm(String realName, String sex, String company, String address, String phone, String email) {
		//this.userName = userName;
		this.realName = realName;
		this.sex = sex;
		this.company = company;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}





	public UserForm(String userName, String password, String realName, String sex, String role, String company, String address, String phone, String email,String currency) {
		super();
		this.userName = userName;
		this.password = password;
		this.realName = realName;
		this.sex = sex;
		this.role = role;
		this.company = company;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.currency=currency;
	}
	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
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


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
