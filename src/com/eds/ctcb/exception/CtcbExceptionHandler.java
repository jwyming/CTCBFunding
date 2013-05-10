package com.eds.ctcb.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.springframework.dao.DataAccessException;

import com.eds.ctcb.util.ActionMsgsUtil;

public class CtcbExceptionHandler extends ExceptionHandler {
	private static Logger log = Logger.getLogger(CtcbExceptionHandler.class);
	
    public ActionForward execute
	(Exception ex, ExceptionConfig ae, ActionMapping mapping,
	 ActionForm formInstance, HttpServletRequest request,
	 HttpServletResponse response)
	throws ServletException {
    	if(ex != null && ex instanceof BizException){    		
    		BizException bizException = (BizException)ex;       		
    		bizException.saveMessage(request, "");
    		if(bizException.getCause()!=null){
    			log.error(this.e2s((bizException.getCause())));
    		}
    	}else if(ex != null && ex instanceof DataAccessException){
    		log.error(this.e2s(ex));
    		ActionMsgsUtil.saveErrorMessage(request, "common.error.dbAccess", null, "");
    	}else if(ex != null ){
    		log.error(this.e2s(ex));
    		ActionMsgsUtil.saveErrorMessage(request, "common.error.unknown", null, "");
    	}else{
    		ActionMsgsUtil.saveErrorMessage(request, "common.error.unknown", null, "");
    	}

		ActionForward forward = mapping.findForward("showMsg");
		return forward;
    }
    
    private String e2s(Throwable e){
    	if(e==null){
    		return "";
    	}
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        StringBuffer sb = stringWriter.getBuffer();
        return sb.toString();
    }
}
