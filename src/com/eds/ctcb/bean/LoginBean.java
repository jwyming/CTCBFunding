package com.eds.ctcb.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginBean {
	private long userId;
	private String userName;
	private long loginTime;
	private long roleId;
	private int userStatus;
	private List<String> resourceList = new ArrayList<String>();
	private List<String> locationList = new ArrayList<String>();
	private List<MenuBean> menuList = new ArrayList<MenuBean>();
	private HashMap<String,String> menuStructureMap = new HashMap<String,String>() ;
	
	
	public static HashMap loginMap = new HashMap();
	
	public List<String> getLocationList() {
		return locationList;
	}
	public void setLocationList(List<String> locationList) {
		this.locationList = locationList;
	}
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	public List<MenuBean> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<MenuBean> menuList) {
		this.menuList = menuList;
	}
	public List<String> getResourceList() {
		return resourceList;
	}
	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public HashMap<String, String> getMenuStructureMap() {
		return menuStructureMap;
	}
	public void setMenuStructureMap(HashMap<String, String> menuStructureMap) {
		this.menuStructureMap = menuStructureMap;
	}
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}	
	

	

}
