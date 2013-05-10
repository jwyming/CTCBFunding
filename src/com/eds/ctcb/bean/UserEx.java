package com.eds.ctcb.bean;

public class UserEx {
	
	Long userExId;
	private String userName;
	private String realName;
	private Integer sex;
	private String role;
	private String phone;
	private String email;
	
	
	
	public UserEx() {
		
	}
	
	public Long getUserExId() {
		return userExId;
	}

	public void setUserExId(Long userExId) {
		this.userExId = userExId;
	}



	public UserEx(String userName, String realName, Integer sex, String role, String phone, String email) {
		super();
		this.userName = userName;
		this.realName = realName;
		this.sex = sex;
		this.role = role;
		this.phone = phone;
		this.email = email;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
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
	
	
	

}
