package com.eds.ctcb.action;

import java.io.File;

import servletunit.struts.MockStrutsTestCase;

public class TestBaseAction extends MockStrutsTestCase {
	protected void setUp() throws Exception {
		super.setUp();
		setContextDirectory(new File("WebContent"));
		setConfigFile("/WEB-INF/config/struts-config.xml");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testNoParameters() {

		setRequestPathInfo("/login");
		actionPerform();
		verifyInputForward();
		String[] actionErrors = { "errors.required", "errors.required" };
		verifyActionErrors(actionErrors);
		verifyInputForward();
	}

	public void testOneParameters() {
		setRequestPathInfo("/login");
		addRequestParameter("name", "jenny");
		actionPerform();
		verifyInputForward();
		String[] actionErrors = { "errors.required" };
		verifyActionErrors(actionErrors);
		verifyActionErrors(actionErrors);
		verifyInputForward();

	}

	public void testTwoParameters() {
		setRequestPathInfo("/login");
		addRequestParameter("name", "jenny");
		addRequestParameter("psw", "hi");
		actionPerform();
		verifyInputForward();
		String[] actionErrors = { "errors.required" };
		verifyActionErrors(actionErrors);
		verifyActionErrors(actionErrors);
		verifyInputForward();

	}
}
