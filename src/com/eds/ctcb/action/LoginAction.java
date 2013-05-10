package com.eds.ctcb.action;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.form.LoginForm;
import com.eds.ctcb.util.ActionMsgsUtil;
import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.constant.UserStatus;

public class LoginAction extends BaseAction {
    public ActionForward login(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    {
    	LoginForm loginForm = (LoginForm)form;
    	LoginBean loginBean = null;
    	try{
    		loginBean = this.privBiz.login(request, loginForm);
    	}catch(BizException e){
    		e.saveMessage(request, "");
    		return mapping.findForward("failure");
    	}
    	
    	if(loginBean!=null && loginBean.getUserStatus() == UserStatus.INIT){
    		return mapping.findForward("chgPwd");    		
    	}else{
    		return mapping.findForward("success");
    	}
        
    }
    
    public ActionForward logout(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    {
    	this.privBiz.logout(request);
        return mapping.findForward("success");
    }
    
    public ActionForward noPriv(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    {
		ActionMsgsUtil.saveErrorMessage(request, "common.error.noPriv", null, "");
        return mapping.findForward("showMsg");
    }
}
