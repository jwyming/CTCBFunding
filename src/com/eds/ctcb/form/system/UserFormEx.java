package com.eds.ctcb.form.system;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.form.BaseForm;

public class UserFormEx extends BaseForm {
	private Long id;
	private String userName;
	private String realName;
	private Integer sex;
	private Long role;
	private String company;
	private String address;
	private String phone;
	private String email;
	
	
	
	public UserFormEx(String userName, String realName, Integer sex, Long role, String company, String address, String phone, String email) {
		this.userName = userName;
		this.realName = realName;
		this.sex = sex;
		this.role = role;
		this.company = company;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}
	
	

	public UserFormEx() {
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

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ActionErrors doValidate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
