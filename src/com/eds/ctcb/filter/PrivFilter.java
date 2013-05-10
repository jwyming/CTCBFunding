package com.eds.ctcb.filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eds.ctcb.bean.LoginBean;

public class PrivFilter extends BaseFilter{

	public void doFilterEx(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws IOException, ServletException{  
//        if (1==1) {//--------------
//       	chain.doFilter(request, response);
//        	return;
//        }
		
		
		//1.Free Access Url
        if (isFreeAccessUrl(request)) {
        	chain.doFilter(request, response);
        	return;
        }
        //2.Login Check
    	LoginBean loginInfo = this.getLoginBean(request);
    	if(loginInfo == null){            		
    		response.sendRedirect(request.getContextPath()+ "/preLogin.do");
    		return;
    	}
    	//3.Single Instance Login Check
		long loginTime1 = loginInfo.getLoginTime();
		long loginTime2 = ((Long)(LoginBean.loginMap.get(loginInfo.getUserName()))).longValue();
		if(loginTime1 != loginTime2){
			response.sendRedirect(request.getContextPath()+ "/logout.do");
    		return;
		}
		//4.Privlege Check
		List<String> expectedUrlList =  this.getLoginBean(request).getLocationList();
		if(!this.isExpectedUrl(request, expectedUrlList)){
			response.sendRedirect(request.getContextPath()+ "/noPriv.do");
			return;
		}
        chain.doFilter(request, response);
	}
	


	private boolean isFreeAccessUrl(HttpServletRequest request){//????????
		List<String> expectedUrlList = new ArrayList<String>();
		expectedUrlList.add("login.do");
		expectedUrlList.add("preLogin.do");
		expectedUrlList.add("logout.do");
		expectedUrlList.add("showMsg.do");
		expectedUrlList.add("noPriv.do");
        if (this.isExpectedUrl(request, expectedUrlList)) {
        	return true;
        }else{
        	return false;
        }
	}
	
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
