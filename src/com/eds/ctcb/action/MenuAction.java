package com.eds.ctcb.action;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.ForwardAction;

import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.common.SessionKey;

public class MenuAction extends ForwardAction {
    public ActionForward execute
	(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	 HttpServletResponse response)
	throws Exception {
    	SessionKey.cleanSession(request);
    	String currentMenuLink = request.getRequestURL().toString();
    	request.getSession().setAttribute(SessionKey.CURRENT_MENU_LINK, currentMenuLink);
    	try{
	    	LoginBean loginBean = (LoginBean)(request.getSession().getAttribute(SessionKey.GLOBAL_LOGIN_INFO));
	    	HashMap<String,String> menuStructureMap = loginBean.getMenuStructureMap();
	    	String[] urlSegArray = request.getRequestURI().split("/");
	    	String doMethod = urlSegArray[urlSegArray.length-1];
	    	String menuStructure = menuStructureMap.get(doMethod);
	    	String[] menuStructureArray = menuStructure.split(";");
	    	request.getSession().setAttribute(SessionKey.GLOBAL_MENU_CLICK_TAB, menuStructureArray[0]);
	    	request.getSession().setAttribute(SessionKey.GLOBAL_MENU_CLICK_BAR, menuStructureArray[1]);
    	}catch(Exception e){
	    	request.getSession().setAttribute(SessionKey.GLOBAL_MENU_CLICK_TAB, "-1");
	    	request.getSession().setAttribute(SessionKey.GLOBAL_MENU_CLICK_BAR, "-1");
    	}
    	
    	
    	return super.execute(mapping, form, request, response);
    }
}
