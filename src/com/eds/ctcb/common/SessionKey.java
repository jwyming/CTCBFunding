package com.eds.ctcb.common;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.eds.ctcb.form.LoginForm;
import com.eds.ctcb.form.deal.IrregularInvestmentChangeForm;
import com.eds.ctcb.form.deal.RegularInvestmentChangeForm;
import com.eds.ctcb.form.report.FundPerformanceForm;
import com.eds.ctcb.form.report.InvestmentOrderForm;
import com.eds.ctcb.form.system.HandleTariffForm;
import com.eds.ctcb.form.system.MiscSettingForm;
import com.eds.ctcb.form.system.SimpleUserForm;
import com.eds.ctcb.form.system.TransferTariffForm;
import com.eds.ctcb.form.system.UserForm;
import com.eds.ctcb.form.system.UserFormEx;
import com.eds.ctcb.form.system.UserQryForm;
public class SessionKey {	
	public final static String GLOBAL_LOGIN_INFO = "LOGIN_INFO";	
	public final static String GLOBAL_LOCALE = "org.apache.struts.action.LOCALE";//!!!please don't change this value
	public final static String GLOBAL_MENU_CLICK_TAB = "clickTab";
	public final static String GLOBAL_MENU_CLICK_BAR = "clickBar";
	
	public final static String CURRENT_MENU_LINK="CURRENT_MENU_LINK";
	
	public final static String FORM_LOGIN = LoginForm.class.getSimpleName();
	public final static String FORM_HANDLE_TARIFF = HandleTariffForm.class.getSimpleName();
	public final static String FORM_TRANSFER_TARIFF=TransferTariffForm.class.getSimpleName();
	public static final String FORM_USER = UserForm.class.getSimpleName();
	public static final String FORM_USER_EX = UserFormEx.class.getSimpleName();
	
	public static final String FORM_SIMPLE_USER = SimpleUserForm.class.getSimpleName();
	public static final String FORM_IRREGULAR_CHANGE = IrregularInvestmentChangeForm.class.getSimpleName();
	public static final String FORM_REGULAR_CHANGE = RegularInvestmentChangeForm.class.getSimpleName();
	public static final String FORM_USER_QRY = UserQryForm.class.getSimpleName();	
	public static final String FORM_INVESTMENT_ORDER=InvestmentOrderForm.class.getSimpleName();
	public static final String FORM_FUND_PERFORMANCE=FundPerformanceForm.class.getSimpleName();
	public static final String FORM_MISC_SETTING =MiscSettingForm.class.getSimpleName();	
	
	public final static String MAP_FUND_TYPE = "MAP_FUND_TYPE";
	public final static String MAP_FUND_COMPANY = "MAP_FUND_COMPANY";
	public final static String MAP_ACCOUNT_CURRENCY = "MAP_ACCOUNT_CURRENCY";
	public final static String MAP_FUND1 = "MAP_FUND1";
	public final static String MAP_FUND2 = "MAP_FUND2";
	public static final String MAP_FUND3 = "MAP_FUND3";
	public final static String MAP_ROLE="MAP_ROLE";
	public final static String MAP_ROLE1="MAP_ROLE1";
	public static final String MAP_CURRENCY = "MAP_CURRENCY";
	public static final String MAP_INCREMENT = "MAP_INCREMENT";
	public static final String MAP_CUST_FUND = "MAP_CUST_FUND";
	public static final String MAP_CUST_FUND_COMPANY = "MAP_CUST_FUND_COMPANY";

	public static final String COMPETITION_TOPIC = "COMPETITION_TOPIC";
	public static final String QUARTER_MAP = "QUARTER_MAP";
	public static final String TOPIC_MAP = "TOPIC_MAP";

	
	private static List<String> GLOBAL_SESSION_KEY_LIST = new ArrayList<String>();
	
	static{		
		GLOBAL_SESSION_KEY_LIST.add(GLOBAL_LOGIN_INFO);
		GLOBAL_SESSION_KEY_LIST.add(GLOBAL_LOCALE);
		GLOBAL_SESSION_KEY_LIST.add(GLOBAL_MENU_CLICK_TAB);
		GLOBAL_SESSION_KEY_LIST.add(GLOBAL_MENU_CLICK_BAR);
	}
	
	public static void cleanSession(HttpServletRequest request){
		if(request == null || request.getSession() == null ){
			return;
		}		
		HttpSession session = request.getSession();
		Enumeration e = session.getAttributeNames();
		if(e==null){
			return;
		}
		while(e.hasMoreElements()){
			String s = e.nextElement().toString();
			if(!GLOBAL_SESSION_KEY_LIST.contains(s)){
				session.removeAttribute(s);
			}
		}		
	}
}
