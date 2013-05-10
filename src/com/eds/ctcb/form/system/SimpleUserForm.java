package com.eds.ctcb.form.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.form.BaseForm;

public class SimpleUserForm extends BaseForm {
	private Long id;
	private String realName;
	private String sex;
	private String company;
	private String address;
	private String phone;
	private String email;
	
	

	public SimpleUserForm() {
		
	}
	
	public SimpleUserForm(String realName, String sex, String company, String address, String phone, String email) {
		this.realName = realName;
		this.sex = sex;
		this.company = company;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}
	
	
	

	public SimpleUserForm(Long id, String realName, String sex, String company, String address, String phone, String email) {
		this.id = id;
		this.realName = realName;
		this.sex = sex;
		this.company = company;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
