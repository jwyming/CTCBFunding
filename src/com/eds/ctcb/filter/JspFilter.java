package com.eds.ctcb.filter;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class JspFilter extends BaseFilter{

	public void destroy() {
	}

	public void doFilterEx(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws IOException, ServletException {
        if(request.getRequestURL().toString().endsWith(request.getContextPath() + "/")) {
            response.sendRedirect(request.getContextPath()+ "/preLogin.do");
    		return;
        }
        response.sendRedirect(request.getContextPath()+ "/noPriv.do");
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
