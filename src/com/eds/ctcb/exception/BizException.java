package com.eds.ctcb.exception;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import com.eds.ctcb.util.ActionMsgsUtil;
import com.eds.ctcb.util.I18NUtil;

import org.apache.log4j.Logger;
public class BizException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	private String errorMsgI18N;
	private Object[] errorParamArray;
	private Throwable originException;
	
	private static Logger log = Logger.getLogger(BizException.class);
	public BizException(String errorCode, String errorMsgI18N, Object[] errorParamArray, Throwable originException) {
		super();
		this.errorCode = errorCode;
		this.errorMsgI18N = errorMsgI18N;
		this.errorParamArray = errorParamArray;
		this.originException = originException;
		log.error(this.getErrorMsg(null));
		if(originException!=null){
			log.error(originException.getStackTrace());
		}
	}
	
	public void saveMessage(HttpServletRequest request,String JumpUrl){
		if(errorMsgI18N != null && !errorMsgI18N.trim().equals("")){
			ActionMsgsUtil.saveErrorMessage(request, errorMsgI18N, errorParamArray, JumpUrl);
		}
	}	
	
	public String getErrorMsg(HttpServletRequest request){
		String msg = "";
		if(this.errorMsgI18N!=null && this.errorMsgI18N.trim().length()>0){
			msg = I18NUtil.getResourceBundle(request).getString(this.errorMsgI18N);
			if(this.errorParamArray!=null && this.errorParamArray.length>0){
				msg = MessageFormat.format(msg, this.errorParamArray);
			}
		}
		return msg;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsgI18N() {
		return errorMsgI18N;
	}


	public void setErrorMsgI18N(String errorMsgI18N) {
		this.errorMsgI18N = errorMsgI18N;
	}


	public Object[] getErrorParamArray() {
		return errorParamArray;
	}

	public void setErrorParamArray(Object[] errorParamArray) {
		this.errorParamArray = errorParamArray;
	}
	
	public Throwable getOriginException() {
		return originException;
	}
	public void setOriginException(Throwable originException) {
		this.originException = originException;
	}

}
