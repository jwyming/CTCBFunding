package com.eds.ctcb;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Set;

import com.eds.ctcb.util.DataUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.eds.ctcb.constant.*;
import com.eds.ctcb.dao.account.CurrencyDao;
import com.eds.ctcb.dao.account.UserDao;
import com.eds.ctcb.dao.priv.RoleDao;
import com.eds.ctcb.dao.system.SysParamDao;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.CashAccount;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Role;
import com.eds.ctcb.db.SysParam;
import com.eds.ctcb.db.User;
import com.eds.ctcb.db.UserRole;
import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.bean.UserEx;
import com.eds.ctcb.biz.BizFactory;
import com.eds.ctcb.biz.system.UserBiz;
import com.eds.ctcb.form.system.SysParamForm;
import com.eds.ctcb.form.system.UserQryForm;

public class DavidTest {

	public static void main(String[] args) 
	{
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[]{"config/Hibernate.xml","config/Dao.xml","config/Biz.xml","config/Aop.xml"}
				);
		BizFactory bizFactory=(BizFactory)ac.getBean("bizFactory");
		//BizFactory bizFactory=BizFactory.getInstance();
		SysParamDao sysParamDao=(SysParamDao)ac.getBean("sysParamDao");
		RoleDao roleDao=(RoleDao)ac.getBean("roleDao");
		UserDao userDao=(UserDao)ac.getBean("userDao");
		CurrencyDao currencyDao=(CurrencyDao)ac.getBean("currencyDao");
		UserBiz userBiz=(UserBiz)ac.getBean("userBiz");
		
	
		UserQryForm qryForm=new UserQryForm();
		//qryForm.setSex("1");
		qryForm.setUserName("a");
		qryForm.setRole("1");
	
		
		PageBean pageBean=userBiz.qryUserWithRoleNameInPage(qryForm, 11, 1);
		List list=pageBean.getList();
		int i=pageBean.getFullListSize();
		System.out.println("count:"+i);
		Iterator it=list.iterator();
		while(it.hasNext()){
			UserEx ue=(UserEx)it.next();
			System.out.println("name:"+ue.getUserName()+" realName:"
					+ue.getRealName()+" Email:"+ue.getEmail()+" phone:"+ue.getPhone()
					+" Role:"+ue.getRole()+ " Sex:"+ue.getSex());
		}
		
		/*UserQryForm form=null;
		//QryBean qryBean=userBiz.qryFormToQryBean(form);
		PageBean pageBean=userBiz.qryUserWithRoleNameInPage(form, 4, 1);
		List list=pageBean.getList();
		Iterator it=list.iterator();
		/*UserRole userRole=(UserRole)it.next();
		Role role=userRole.getRole();
		System.out.println("id="+role.getId()+" Name="+role.getName()+" Remark="+role.getRemark());*/
		
		/*while(it.hasNext()){
			//UserRole userRole=(UserRole)it.next();
			//Role role=userRole.getRole();
			Object [] row =(Object[])it.next();
			String userName=row[0].toString();
			String realName=row[1].toString();
			String sex=row[2].toString();
			String roleName=row[3].toString();
			String phone=row[4].toString();
			String email=row[5].toString();
			
			System.out.println(userName+" "+realName+" "+sex+" "+roleName+" "+phone+" "+email);
		}
		
		
		/*Currency currency=currencyDao.getCurrencyByType(CurrencyType.NTD);
		System.out.println(currency.getId()+" "+currency.getName()+" "+currency.getType()+""+currency.getRemark());
		/*List<Role> roleList=userDao.getRoleList();
		
		Iterator it=roleList.iterator();
		while(it.hasNext()){
			Role role=(Role)it.next();
			System.out.println(role.getId()+role.getName());
		}
		/*List<String> roleName=userDao.getRoleNameList();
		Iterator it=roleName.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		Role role=roleDao.getRoleByName("一般用戶");
		System.out.println(role.getName()+"Remarks:"+ role.getRemark());
		
		
		//Role role=roleDao.getRoleByName("一般用戶");
		//System.out.println(role.getId()+":"+role.getName()+":"+role.getRemark());*/
		
		
		 /*======================================================
		Currency currency=new Currency("AUD",new Integer("4"),"Taiwan Money");
		userDao.create(currency);
		
		
		CashAccount cashAccount=new CashAccount();
		cashAccount.setCurrency(currency);
		cashAccount.setCount(new BigDecimal("10000"));
		
		
		Account account=new Account();
		
		account.setCreateTime(new Date());
		account.setRemark("this is Non_Frozen cash account");
		account.setType(AccountType.NON_FROZEN_CASH);
		account.setCashAccount(cashAccount);
		cashAccount.setAccount(account);
		
		 //public User(String userName, String password, Date createTime, String name, Integer sex, String company, String address, String post, String email, String phone, Integer status, String remark, String addition, Set accounts, Set userRoles) 
		User user=new User("sparks2",DataUtil.getEncodedStr("liudeli"+"abc"),new Date(),
				"liudeli2",new Integer(1),"EDS","Wuhan","433200",
				"deli.liu@eds.com","88768766",new Integer(1),"this is a user",
				"addition",new HashSet(),new HashSet());
		
		Role role=roleDao.getRoleByName("一般用戶");
		UserRole ur=new UserRole(null,user,role);
		user.getUserRoles().add(ur);
		userDao.create(user);	

		account.setUser(user);
		user.getAccounts().add(account);
		
		
		userDao.create(cashAccount);
		//==============================================================================*/
	
		
		
		/*=============================2 type account===============================
		Currency currency=new Currency("NZD",new Integer("4"),"New Zealand Money");
		userDao.create(currency);
		
		
		CashAccount cashAccount1=new CashAccount();
		cashAccount1.setCurrency(currency);
		cashAccount1.setCount(new BigDecimal("10000"));
		
		CashAccount cashAccount2=new CashAccount();
		cashAccount2.setCurrency(currency);
		cashAccount2.setCount(new BigDecimal("100"));
		
		
		Account account=new Account();
		
		account.setCreateTime(new Date());
		account.setRemark("this is Non_Frozen cash account");
		account.setType(AccountType.NON_FROZEN_CASH);
		account.setCashAccount(cashAccount1);
		cashAccount1.setAccount(account);
		
		Account account2=new Account();
		account2.setCreateTime(new Date());
		account2.setRemark("this is Frozen cash account");
		account2.setType(AccountType.FROZEN_CASH);
		account2.setCashAccount(cashAccount2);
		
		cashAccount2.setAccount(account2);
		
		 //public User(String userName, String password, Date createTime, String name, Integer sex, String company, String address, String post, String email, String phone, Integer status, String remark, String addition, Set accounts, Set userRoles) 
		User user=new User("sparks1",DataUtil.getEncodedStr("liudeli"+"abc"),new Date(),
				"liudeli1",new Integer(1),"EDS","Wuhan","433200",
				"deli.liu@eds.com","88768766",new Integer(1),"this is a user",
				"addition",new HashSet(),new HashSet());
		
		Role role=roleDao.getRoleByName("一般用戶");
		UserRole ur=new UserRole(null,user,role);
		user.getUserRoles().add(ur);
		userDao.create(user);	

		account.setUser(user);
		user.getAccounts().add(account);
		account2.setUser(user);
		user.getAccounts().add(account2);
		
		
		userDao.create(cashAccount1);
		userDao.create(cashAccount2);
	
		==========================================================================================*/
		/*user.getAccounts().add(account);
		Role role=roleDao.getRoleByName("一般用戶");
		UserRole ur=new UserRole(null,user,role);
		user.getUserRoles().add(ur);
		userDao.create(user);	
		
		//userDao.create(account);
		
		/*currency=new Currency("USD","Americian Money");
		
		cashAccount=new CashAccount();
		cashAccount.setCurrency(currency);
		cashAccount.setCount(new BigDecimal("20000"));
		account=new Account();
		account.setCashAccount(cashAccount);
		account.setCreateTime(new Date());
		account.setRemark("this is  frozen cash account");
		account.setType(AccountType.FROZEN_CASH);
		
		user.getAccounts().add(cashAccount);*/
		
		
		
		//userDao.createUser(user, "一般用戶");
		
		
		/*UserRole ur=new UserRole(null,user,role);
		user.getUserRoles().add(ur);
		userDao.create(user);
		
	
		/*SysParam sp=new SysParam();
		sp.setName(SysParameter.INIT_PASSWORD);
		sp.setValue("888885");
		sp.setRemark("it is new added");
		
		sysParamDao.updateSysParam(sp);
		/*sp.setName(SysParameter.HIGHEST_HANDLE_TARIFF);
		sp.setValue("50");
		sp.setRemark("it is new added");
		sysParamDao.updateSysParam(sp);*/
		
		
		
		/*SysParamForm sysParamForm=new SysParamForm();
		
		sysParamForm.setName(SysParameter.INIT_PASSWORD);
		sysParamForm.setValue("888885");
		sysParamForm.setRemark("this is updated system password");
		bizFactory.getSystemManagementBiz().updateOneParameter(sysParamForm);
		
		/*sysParamForm.setName(SysParameter.INIT_INV_AMOUNT);
		sysParamForm.setValue("10000.00");
		sysParamForm.setRemark("this is initial investion amount");
		bizFactory.getSystemManagementBiz().createInitInvestAmount(sysParamForm);
		sysParamForm.setName(SysParameter.HANDLE_RATE);
		sysParamForm.setValue("0.28");
		sysParamForm.setRemark("this is handle rate");
		bizFactory.getSystemManagementBiz().updateOneParameter(sysParamForm);
		/*SysParamDao sysParamDao=(SysParamDao)ac.getBean("sysParamDao");*/
		//System.out.println(sysParamDao.findSysParamByName(SysParameter.HANDLE_RATE).getName());
		
	}
}
