package com.eds.ctcb.filter;
import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eds.ctcb.bean.LoginBean;
import com.eds.ctcb.common.SessionKey;
public abstract class BaseFilter implements Filter{

	protected LoginBean getLoginBean(HttpServletRequest request){
		LoginBean loginInfo = (LoginBean)(request.getSession().getAttribute(SessionKey.GLOBAL_LOGIN_INFO));
		return loginInfo;
	}
	
	protected boolean isExpectedUrl(HttpServletRequest request,List<String> expectedUrlList){
		if(expectedUrlList!=null){
			for(String url : expectedUrlList){
				if(request.getRequestURL().toString().indexOf(url)>=0 ){
					return true;
				}
			}
		}
		return false;
	}

	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		if (request instanceof HttpServletRequest) {
        	HttpServletRequest httpRequest = (HttpServletRequest)request;
        	HttpServletResponse httpResponse = (HttpServletResponse)response;        	
        	this.doFilterEx(httpRequest, httpResponse, chain);
        }else{
        	return;//????????????
        }
	}
	
	public abstract void doFilterEx(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws IOException, ServletException ;


}
