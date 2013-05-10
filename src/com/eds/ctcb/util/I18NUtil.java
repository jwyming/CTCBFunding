package com.eds.ctcb.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

public class I18NUtil {
	private static HashMap<Locale,ResourceBundle> resourceBundleMap = new HashMap<Locale,ResourceBundle>();
	
	
	
	public static ResourceBundle getResourceBundle(HttpServletRequest request){
		Locale locale = Locale.CHINA;
		if(request!=null){
			locale = request.getLocale();	
		}

		ResourceBundle rb = (ResourceBundle)(resourceBundleMap.get(locale));
		if(rb == null){
			rb = ResourceBundle.getBundle("ApplicationResources",locale);
			resourceBundleMap.put(locale, rb);
		}
		
		

		return rb;
	}
	

}
