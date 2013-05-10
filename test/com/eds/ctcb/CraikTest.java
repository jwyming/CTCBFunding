package com.eds.ctcb;

import com.eds.ctcb.dao.account.ExchangeRateDao;
import com.eds.ctcb.dao.account.FundEquityDao;
import com.eds.ctcb.db.Currency;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class CraikTest {
	public static void main(String[] args){
    System.out.println("time=" + Calendar.getInstance().getTime());
    ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[]{"config/Hibernate.xml","config/Dao.xml","config/Biz.xml","config/Aop.xml"}
				);
//		BizFactory bizFactory=(BizFactory)ac.getBean("bizFactory");
//		System.out.println(stu.getName());
//		Fund fund = new Fund();
//		System.out.println(fund.getCode());
//		FundEquity fundEquity = new FundEquity();
//
//		BaseDao bd = (BaseDao)ac.getBean("fundEquityDao");
//		BaseDao baseDao = (BaseDao)ac.getBean("FundDao");
		
//1		
//		FundDao fundDao = (FundDao)ac.getBean("fundDao");
//		List<Fund> fund = fundDao.getFundById(1L);
//		for(Fund fundList: fund){
//		System.out.println("Name : " +  fundList.getName() + "  Code : " + fundList.getCode());}
		
//2	
//		FundEquityDao fundEquityDao = (FundEquityDao) ac.getBean("fundEquityDao");
//		BigDecimal equity = fundEquityDao.getFundEquity(1L, new Date());
//    System.out.println(equity);
    
//    	Currency scurrency = new Currency();
//    	Currency dcurrency = new Currency();
//    	ExchangeRateDao exchangeRateDao = (ExchangeRateDao)ac.getBean("exchangeRateDao");
//    	BigDecimal exchangeRate = exchangeRateDao.getExchangeRate(inSCurrency, inDCurrency, inDate)
    	
    	
    	


	}

}