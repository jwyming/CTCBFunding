package com.eds.ctcb.bean;

import java.util.ArrayList;
import java.util.List;

public class MenuBean {
	private int id;

	private String title;

	private String href;

	private List<MenuBean> subMenu = new ArrayList<MenuBean>();

	public MenuBean(int id, String title, String href) {
		this.id = id;
		this.title = title;

		this.href = href;
	}

	public void addSubMenu(int id, String title,	 String href) {
		this.subMenu.add(new MenuBean(id, title,  href));
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<MenuBean> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<MenuBean> subMenu) {
		this.subMenu = subMenu;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
