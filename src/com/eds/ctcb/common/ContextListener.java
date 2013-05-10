package com.eds.ctcb.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.eds.ctcb.util.AppContextUtil;
public class ContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		AppContextUtil.getApplicationContext();
	}
	

}
