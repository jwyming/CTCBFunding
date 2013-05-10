package com.eds.ctcb;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eds.ctcb.dao.system.LogDao;
import com.eds.ctcb.db.Log;




public class WzyTest {
	
	public static void change(String s){
		s = "abcdefg";
	}

	public static void main(String[] args) 
	{
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[]{"config/Hibernate.xml","config/Dao.xml","config/Biz.xml"}
				);
		
		LogDao logDao = (LogDao)(ac.getBean("logDao"));
		Log log = (Log)(logDao.findById(Log.class, new Long(10020l)));
		System.out.println(1);
	}
}
