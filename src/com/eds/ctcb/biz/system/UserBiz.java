package com.eds.ctcb.biz.system;

import java.util.List;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.db.Currency;
import com.eds.ctcb.db.Role;
import com.eds.ctcb.db.User;
import com.eds.ctcb.form.system.UserForm;
import com.eds.ctcb.form.system.UserQryForm;

public interface UserBiz {
	//public abstract void createUserWithRole(UserForm userForm);
	//public  User userForm2Entity(UserForm form);
	public abstract void resetUserPassword(Long id);
	public abstract void deleteUser(Long deletedId,Long loginUserId,String logContent);
	public abstract User getUserById(Long userId);
	public List<String> getRoleNameList();
	public User getUserByUserName(String userName);
	public User getUserByUserNameForAll(String userName);
	public List<Role> getRoleList();
	public List<Currency> getAllCurrency();
	public List<Currency> getNtdCurrency();

	public  PageBean qryUserWithRoleNameInPage(UserQryForm form,int pageSize,int pageNumber);
	 public void updateUserInfo(User user);
	 public Role getRoleByName(String name);
	 public void updateUserRole(User user,Role role);
	 public Role getRoleById(Long id);
	 public Role getRoleByUserId(Long id);
	 public void createUserSeparetely(UserForm userForm,Long loginUserId,String logContent);
}
