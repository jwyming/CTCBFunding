package com.eds.ctcb;


import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eds.ctcb.biz.report.ReportBiz;
import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.dao.account.UserDao;
import com.eds.ctcb.dao.report.ReportDataDao;
import com.eds.ctcb.db.ReportData;
import com.eds.ctcb.db.ReportInfo;
import com.eds.ctcb.db.User;

public class ApplicationContextTest {

	public static void main(String[] args) 
	{
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[]{"config/Hibernate.xml","config/Dao.xml","config/Biz.xml"}
				);
		
		BaseDao baseDao = (BaseDao)(ac.getBean("baseDao"));
		Date today = baseDao.getSysdate();
		System.out.println(today);
        //======================================rex test code=============================		

		ReportBiz reportBiz=(ReportBiz)(ac.getBean("reportBiz"));
		Boolean tag=reportBiz.makeReportData();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+tag);

//		List list=reportBiz.getLatest4ComptitionTopic();
//		Iterator it=list.iterator();
//		while(it.hasNext()){
//			ReportInfo reportData=(ReportInfo)it.next();
//			System.out.println(reportData.getYear()+"---"+reportData.getQuarter()+"---"+reportData.getTopic());
//		}
//		
//		 ReportDataDao reportDataDao=(ReportDataDao)(ac.getBean("reportDataDao"));
//		 List list1=reportDataDao.getSavingPlanKingReportData(new Long(4));
//		 System.out.println("--------------------------------------------------------size="+list1.size());
//		
//		UserDao userDao=(UserDao)(ac.getBean("userDao"));
//		List <User> list2=userDao.getAllNotDeletedUsualUserList();
//		for(User user:list2){
//			System.out.println(user.getName()+"----"+user.getUserName());
//		}
		//===================================================================================
  }
}
