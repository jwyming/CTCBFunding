package com.eds.ctcb.biz.system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.biz.BaseBiz;
import com.eds.ctcb.common.LogEx;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.LogType;
import com.eds.ctcb.constant.RoleType;
import com.eds.ctcb.constant.UserStatus;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.CashAccount;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Role;
import com.eds.ctcb.db.User;
import com.eds.ctcb.db.UserRole;
import com.eds.ctcb.form.system.UserForm;
import com.eds.ctcb.form.system.UserQryForm;
import com.eds.ctcb.util.DataUtil;

public class UserBizImpl extends BaseBiz implements UserBiz{
	private static LogEx log = new LogEx(UserBizImpl.class);

	private void createUserWithRole(UserForm userForm) {
		Long roleId=Long.parseLong(userForm.getRole());
		User user=userForm2Entity(userForm);
		this.userDao.createUser(user,roleId);
		//this.userDao.createUser(user, roleName);	
	}
	
	/*public User(String userName, String password, Date createTime, String name, Integer sex, String company, String address, String post, String email, String phone, Integer status, String remark, String addition, Set accounts, Set userRoles) 
	*/
	
	private  User userForm2Entity(UserForm form){
		String addition=this.getRandKeys(30);
		User user = null;
		if(form != null){
			user = new User(form.getUserName(),DataUtil.getEncodedStr(form.getPassword()+addition),
					this.userDao.getSysdate(),form.getRealName(),new Integer(form.getSex()),
					form.getCompany(),form.getAddress(),null,form.getEmail(),form.getPhone(),
					new Integer(UserStatus.INIT),"This is a new User",addition,new HashSet(),new HashSet());
		
		}else{
			log.error("the UserForm is null!");
		}
		
		return user;
	}
	

	private String getRandKeys( int intLength ) {

	    String retStr = "";
	    String strTable = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
	    int len = strTable.length();
	    boolean bDone = true;
	    do {
	        retStr = "";
	        int count = 0;
	        for ( int i = 0; i < intLength; i++ ) {
	            double dblR = Math.random() * len;
	            int intR = (int) Math.floor( dblR );
	            char c = strTable.charAt( intR );
	            if ( ( '0' <= c ) && ( c <= '9' ) ) {
	                count++;
	            }
	            retStr += strTable.charAt( intR );
	        }
	        if ( count >= 2 ) {
	            bDone = false;
	        }
	    } while ( bDone );

	    return retStr;
	}

	public void deleteUser(Long id ,Long loginUserId,String logContent) {
		User user=this.getUserById(id);
		user.setStatus(UserStatus.DELETED);
		this.userDao.update(user);
//		for Logging 
		this.createLog(LogType.USER_OPERATION,loginUserId,logContent);
	}

	public void resetUserPassword(Long id) {
		//when user password reset to initial password,the user statas will
		//be changed to UserStatus.INIT
		User user=this.getUserById(id);
		user.setPassword(DataUtil.getEncodedStr(this.sysParamDao.getInitPwd()+user.getAddition()));
		user.setStatus(UserStatus.INIT);
		this.userDao.update(user);
		
	}

	public User getUserById(Long userId) {
		return this.userDao.getUserByID(userId);
	}
	
	public Role getRoleByName(String name){
		return this.roleDao.getRoleByName(name);
	}
	
	 public List<String> getRoleNameList(){
		return this.userDao.getRoleNameList();
	 }
	 
	public List<Role> getRoleList(){
			return this.userDao.getRoleList();
	}
	 
	 public User getUserByUserName(String userName){
		 return this.userDao.getUserByUserName(userName);
	 }
	 
	 public User getUserByUserNameForAll(String userName){
		 return this.userDao.getUserByUserNameForAll(userName);
		 
	 }
	 
	 public void updateUserInfo(User user){
		 this.userDao.updateUser(user);
	 }
	 
	 public Role getRoleById(Long id){
		 return this.roleDao.getRoleById(id);
	 }
	 
	 public void updateUserRole(User user,Role role){
		 UserRole ur=this.userDao.getUserRoleByUserId(user.getId());
		 ur.setRole(role);
		 this.userDao.updateUserRole(ur);
	 }
	 
	 public Role getRoleByUserId(Long id){
		 return this.userDao.getRoleByUserId(id);
	 }
	 
	 private  QryBean qryForm2QryBean(UserQryForm form){
	    	StringBuffer hqlBuffer = new StringBuffer();
	    	hqlBuffer.append("from UserRole as ur where ur.user.status  <> ?  ");
	    	List<Object> paramList = new ArrayList<Object>();
	    	paramList.add(new Integer(UserStatus.DELETED));
	    	
	    	if(form!=null){
		    	if(form.getUserName()!= null && form.getUserName().trim().length() > 0){
		    		hqlBuffer.append(" and upper(ur.user.userName) like upper(?)");
		    		paramList.add("%"+form.getUserName()+"%");
		    	}
		    	
		    	if(form.getRealName()!= null && form.getRealName().trim().length() > 0){
		    		hqlBuffer.append(" and upper(ur.user.name) like upper(?)");
		    		paramList.add("%"+form.getRealName()+"%");
		    	}
		    	
		    	String role=form.getRole();
		    	if(role != null && role.trim().length() > 0){
		    		String roleName=this.roleDao.getRoleById(new Long(role.trim())).getName();
		    		hqlBuffer.append(" and ur.role.name =? ");
		    		paramList.add(roleName);
		    	}
		    	
		    	if(form.getSex() != null && form.getSex().trim().length() > 0){
		    		hqlBuffer.append(" and ur.user.sex=? ");
		    		paramList.add(new Integer(form.getSex()));
		    	}
	    	}
	    	hqlBuffer.append(" order by ur.user.id asc ");
	    	String hql = hqlBuffer.toString();
	    	Object[] paramArray = paramList.toArray();
	    	return new QryBean(hql,paramArray);
	    }
	 
	 	
	
	 public  PageBean qryUserWithRoleNameInPage(UserQryForm form,int pageSize,int pageNumber) { 
	    	QryBean qryBean = qryForm2QryBean(form);
	   		return this.userDao.qryUserExInPage(qryBean, pageSize, pageNumber);
	  	}
	 
	 
	public List<Currency> getAllCurrency(){
			return this.currencyDao.getAllCurrency();
	}
	
	public List<Currency> getNtdCurrency(){
		return this.currencyDao.getNtdOnly();
	}
	
	private void createUser(UserForm userForm){
		
		//String roleName=userForm.getRole();
		Long roleId=Long.parseLong(userForm.getRole());
		int currencyType=Integer.parseInt(userForm.getCurrency());
		
		Currency currency=this.currencyDao.getCurrencyByType(currencyType);
		BigDecimal initInvAmt=this.sysParamDao.getInitInvAmount();
		
		
		CashAccount cashAccount1=new CashAccount();
		cashAccount1.setCurrency(currency);
		cashAccount1.setCount(initInvAmt);
		
		CashAccount cashAccount2=new CashAccount();
		cashAccount2.setCurrency(currency);
		cashAccount2.setCount(BigDecimal.ZERO);
		
		
		Account account1=new Account();
		
		account1.setCreateTime(this.userDao.getSysdate());
		account1.setRemark("this is Non_Frozen cash account");
		account1.setType(AccountType.NON_FROZEN_CASH);
		account1.setCashAccount(cashAccount1);
		cashAccount1.setAccount(account1);
		
		Account account2=new Account();
		account2.setCreateTime(this.userDao.getSysdate());
		account2.setRemark("this is Frozen cash account");
		account2.setType(AccountType.FROZEN_CASH);
		account2.setCashAccount(cashAccount2);
		cashAccount2.setAccount(account2);
		
		User user=this.userForm2Entity(userForm);
		
		//Role role=roleDao.getRoleByName(roleName);
		Role role=this.roleDao.getRoleById(roleId);
		
		UserRole ur=new UserRole(null,user,role);
		user.getUserRoles().add(ur);
		userDao.create(user);	

		account1.setUser(user);
		user.getAccounts().add(account1);
		account2.setUser(user);
		user.getAccounts().add(account2);
		
		
		userDao.create(cashAccount1);
		userDao.create(cashAccount2);
	}
	
	
public void createUserSeparetely(UserForm userForm,Long loginUserId,String logContent){
	Long roleId=Long.parseLong(userForm.getRole());
		if (roleId.equals(RoleType.USUAL_USER)) {
			this.createUser(userForm);
		}
		if (roleId.equals(RoleType.SYSTEM_ADM)) {
			this.createUserWithRole(userForm);
		}
		
//		for Logging 
		this.createLog(LogType.USER_OPERATION,loginUserId,logContent);

	}
}
