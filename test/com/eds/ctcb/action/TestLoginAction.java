package com.eds.ctcb.action;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.biz.priv.PrivBiz;
import com.eds.ctcb.form.LoginForm;

import servletunit.struts.MockStrutsTestCase;
import static org.easymock.EasyMock.*;


public class TestLoginAction extends MockStrutsTestCase{
	
	private PrivBiz mockPrivBiz;
	
	
	
	protected void setUp() throws Exception{
		super.setUp();
		setContextDirectory(new File("WebContent"));
		setConfigFile("/WEB-INF/config/struts-config.xml");
		mockPrivBiz = createMock(PrivBiz.class);
	}
	
	protected void tearDown()throws Exception{
		super.tearDown();
	}


}
