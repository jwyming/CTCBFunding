package com.eds.ctcb.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ActionMsgsUtil {
	public static final String MSG_KEY_SUCCESS = "MSG_KEY_SUCCESS";	
    public static final String MSG_KEY_ERROR = "MSG_KEY_ERROR";
    public static final String JUMP_URL = "JUMP_URL";
    /**
     * @deprecated
     */
    public static ActionMessages getActionMessages(String msgKey,String msgContent){
        ActionMessages msgs = new ActionMessages();
        msgs.add(msgKey,new ActionMessage(msgContent,false));
        return msgs;
    }
    
    public static ActionMessages getActionMessagesI18N(String msgKey,String msgI18N,Object[] paramArray){
        ActionMessages msgs = new ActionMessages();
        msgs.add(msgKey,new ActionMessage(msgI18N,paramArray));
        return msgs;
    }
    

    
    
    
    private static void saveMessage(HttpServletRequest request,String msgKey,String msgI18N, Object[] paramArray, String jumpUrl)
    {
    	ActionMessages actionMessages = null;
        if (msgI18N != null && !msgI18N.trim().equalsIgnoreCase("")) {
        	actionMessages = getActionMessagesI18N(msgKey,msgI18N,paramArray);
        }   	
        
    	String requestKey = "org.apache.struts.action.ACTION_MESSAGE";
    	
    	if (actionMessages == null || actionMessages.isEmpty()){
    	    request.removeAttribute(requestKey);
    	}else{
    	    request.setAttribute(requestKey,actionMessages);
        }
        request.setAttribute(ActionMsgsUtil.JUMP_URL, jumpUrl);
    }
    
    public static void saveSuccessMessage(HttpServletRequest request,String msgI18N, Object[] paramArray, String jumpUrl)
    {
    	ActionMsgsUtil.saveMessage(request,ActionMsgsUtil.MSG_KEY_SUCCESS, msgI18N, paramArray, jumpUrl);
    }    
   
    public static void saveErrorMessage(HttpServletRequest request,String msgI18N, Object[] paramArray, String jumpUrl)
    {
    	ActionMsgsUtil.saveMessage(request,ActionMsgsUtil.MSG_KEY_ERROR, msgI18N, paramArray, jumpUrl);
    }
    
}
