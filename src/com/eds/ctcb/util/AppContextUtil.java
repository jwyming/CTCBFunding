package com.eds.ctcb.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContextUtil {
	private static ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
	public static ClassPathXmlApplicationContext getApplicationContext(){
		return ac;
	}
}
