package com.eds.ctcb.dao.account;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.bean.UserEx;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.CurrencyType;
import com.eds.ctcb.constant.UserStatus;
import com.eds.ctcb.constant.RoleType;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.dao.priv.RoleDao;
import com.eds.ctcb.db.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
	
	public List<User> getAllNotDeletedUsualUserList() {
		QryBean qryBean = new QryBean(
				"Select t.user from UserRole as t where t.user.status <> ? and t.role.id=?",
				new Object[]{new Integer(UserStatus.DELETED),new Long(RoleType.USUAL_USER)});
		return this.qryInList(qryBean);
		
	}

	public User getUserByUserName(String userName){
		User user = null;
		QryBean qryBean = new QryBean(
				"from User as t where t.userName=? and t.status <> ?",
				new Object[]{userName,new Integer(UserStatus.DELETED)});
		List tempList = this.qryInList(qryBean);
		if(tempList!=null && tempList.size()==1 && tempList.get(0) instanceof User){
			user =(User)(tempList.get(0));
		}
		return user;
	}
	
	public User getUserByUserNameForAll(String userName){
		User user = null;
		QryBean qryBean = new QryBean(
				"from User as t where t.userName=?",
				new Object[]{userName});
		List tempList = this.qryInList(qryBean);
		if(tempList!=null && tempList.size()==1 && tempList.get(0) instanceof User){
			user =(User)(tempList.get(0));
		}
		return user;
		
	}
	
	public UserRole getUserRoleByUserId(Long id){
		UserRole ur = null;
		QryBean qryBean = new QryBean(
				"from UserRole as ur where ur.user.id=? and ur.user.status <> ?",
				new Object[]{id,new Integer(UserStatus.DELETED)});
		List tempList = this.qryInList(qryBean);
		if(tempList!=null && tempList.size()==1 && tempList.get(0) instanceof UserRole){
			ur =(UserRole)(tempList.get(0));
		}
		return ur;
	}

//	public List<User> getAllNotDeletedUserList(){
//		QryBean qryBean = new QryBean(
//				"from User as t where t.status <> ?",
//				new Object[]{new Integer(UserStatus.DELETED)});
//		return this.qryInList(qryBean);
//	}
	
	//create user associated with role 
	
	public void createUser(User user,String roleName) {
		Role role=this.getRoleByName(roleName);
		UserRole ur=new UserRole(null,user,role);
		user.getUserRoles().add(ur);
		this.create(user);		
	}
	
//	create user associated with roleId
	public void createUser(User user,Long roleId) {
		Role role=this.getRoleById(roleId);
		UserRole ur=new UserRole(null,user,role);
		user.getUserRoles().add(ur);
		this.create(user);		
	}
	
	//create user associated with cashAccount(both Frozen and Non_Frozen type) and role
	
	

	public User getUserByID(Long ID) {
		User user = (User) findById(User.class, ID);
		user.getAccounts().size();
		user.getUserRoles().size();
        return user;
  }

	public void updateUser(User user) {
		this.update(user);
		
	}
	
	public void updateUserRole(UserRole ur){
		this.update(ur);
	}

    public Set<Currency> getCashAccountCurrencyList(Long userId) {
        Set<Currency> currencySet = new HashSet<Currency>();
        User user = (User) this.findById(User.class, userId);
        Set<Account> accounts = user.getAccounts();
        for(Account account : accounts) {
            if(account.getType().equals(AccountType.NON_FROZEN_CASH)) {
                currencySet.add(account.getCashAccount().getCurrency());
            }
        }
        return currencySet;
    }
    
    public List<String> getRoleNameList(){
		String hql = "select distinct role.name from Role role";
		QryBean qryBean = new QryBean(hql,new Object[]{});
		return (List<String>)(this.qryInList(qryBean));
	}
    
    public List<Role> getRoleList(){
    	String hql="from Role as role";
    	QryBean qryBean = new QryBean(hql,new Object[]{});
		return (List<Role>)(this.qryInList(qryBean));
    }
    
	public Role getRoleById(Long Id){
		Role role = null;
		QryBean qryBean = new QryBean(
				"from Role as t where t.id=?",
				new Object[]{Id});		
		List tempList = this.qryInList(qryBean);
		if(tempList!=null && tempList.size()==1 && tempList.get(0) instanceof Role){
			role =(Role)(tempList.get(0));
		}
		return role;
	}
    
    public Role getRoleByName(String name) {
		
		Role role = null;
		QryBean qryBean = new QryBean(
				"select t from Role as t where t.name=?",
				new Object[]{name});		
		List tempList = this.qryInList(qryBean);
		if(tempList!=null && tempList.size()==1 && tempList.get(0) instanceof Role){
			role =(Role)(tempList.get(0));
		}
		return role;
	}
    
    public Role getRoleByUserId(Long id){
    	Role role =null;
    	QryBean qryBean=new QryBean("select ur.role from UserRole as ur where ur.user.id=?",new Object[]{id});
    	List tempList = this.qryInList(qryBean);
		if(tempList!=null && tempList.size()==1 && tempList.get(0) instanceof Role){
			role =(Role)(tempList.get(0));
		}
		return role;
    }
    
    
    private List qryUserExInList(final QryBean qryBean,final int firstResult,final int maxResults) {  
    	return   getHibernateTemplate().executeFind(
    		new   HibernateCallback()   {  
    		  public   Object   doInHibernate(Session   s)   
    		  throws   HibernateException,   SQLException   {   
    		  Query   query   =   s.createQuery(qryBean.getHql());
    		  if(qryBean.getParamArray() != null){
    			  for(int i=0;i< qryBean.getParamArray().length;i++){
    				  query.setParameter(i, qryBean.getParamArray()[i]);
    			  }
    		  }
    		  query.setFirstResult(firstResult);
    		  query.setMaxResults(maxResults);   
    		  List   list   =   query.list();   
    		  Iterator it=list.iterator();
    		  List uList=new ArrayList();
    		  while(it.hasNext()){
    			  UserRole ur=(UserRole)it.next();
    			  User user=ur.getUser();
    			 //String roleName=ur.getRole().getName();
    			  
    			 
    			  UserEx userEx=new UserEx(user.getUserName(),user.getName(),user.getSex(),ur.getRole().getName(),
    					  user.getPhone(),user.getEmail());
    			  userEx.setUserExId(user.getId());
    			  uList.add(userEx);
    			  
    		     }
    		     return   uList;   
    		  }
   		  });     	
  	}


   private PageBean preQryUserExInPage(QryBean qryBean,int pageSize,int pageNumber) { 
   		int count = this.getRecordCount(qryBean);
   		List currentList = this.qryUserExInList(qryBean,(pageNumber-1)*pageSize,pageSize);
   		PageBean pageBean = new PageBean(currentList,count,pageSize,pageNumber);
   		return pageBean;
  	}
    
 public PageBean qryUserExInPage(QryBean qryBean,int pageSize,int pageNumber) {
    	return this.preQryUserExInPage(qryBean, pageSize, pageNumber);
    }
}
