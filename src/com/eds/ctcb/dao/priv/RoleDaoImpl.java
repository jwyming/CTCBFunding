package com.eds.ctcb.dao.priv;


import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.Role;

public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {
	
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

}
