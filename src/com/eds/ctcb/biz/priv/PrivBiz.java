package com.eds.ctcb.biz.priv;

import javax.servlet.http.HttpServletRequest;

import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.form.LoginForm;

public interface PrivBiz {

	public abstract LoginBean login(HttpServletRequest request, LoginForm loginForm);
	public abstract void logout(HttpServletRequest request);
	public abstract boolean isAvaibleResource(HttpServletRequest request, String resourceId);
}