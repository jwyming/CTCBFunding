package com.eds.ctcb.dao.priv;

import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.Role;

public class UserRoleDaoImpl extends BaseDaoImpl implements UserRoleDao {
	public Role getRoleByUserId(Long userId){
		Role role = null;
		QryBean qryBean = new QryBean(
				"select t.role from UserRole as t where t.user.id=?",
				new Object[]{userId});		
		List tempList = this.qryInList(qryBean);
		if(tempList!=null && tempList.size()==1 && tempList.get(0) instanceof Role){
			role =(Role)(tempList.get(0));
		}
		return role;
	}
}
