package com.eds.ctcb.dao.account;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Role;
import com.eds.ctcb.db.User;
import com.eds.ctcb.db.UserRole;
import com.eds.ctcb.form.system.UserQryForm;

import java.util.List;
import java.util.Set;

public interface UserDao  extends BaseDao{
	public User getUserByUserName(String userName);
	public void createUser(User user,String roleName);
	public void createUser(User user,Long roleId);
	public User getUserByID(Long ID); 

	public void updateUser(User user) ;


    public Set<Currency> getCashAccountCurrencyList(Long userId);
    
    public List<String> getRoleNameList();
    
    public Role getRoleByName(String name);
    

    public List<Role> getRoleList();  
    
    
    public PageBean qryUserExInPage(QryBean qryBean,int pageSize,int pageNumber) ;
    
	public UserRole getUserRoleByUserId(Long id);
	
	public void updateUserRole(UserRole ur);
	
	public Role getRoleByUserId(Long id);
	
	public List<User> getAllNotDeletedUsualUserList();
	 
	 public Role getRoleById(Long Id);
	 
	public User getUserByUserNameForAll(String userName);
    	
    
}
