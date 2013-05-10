package com.eds.ctcb.dao.priv;

import java.util.ArrayList;
import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.dao.BaseDaoImpl;

public class RoleResourceDaoImpl extends BaseDaoImpl implements RoleResourceDao {
	public List getMenuListOfRole(Long roleId,Long parentResourceId){
		QryBean qryBean = new QryBean(
				"select t.resource from RoleResource as t where t.role.id=? and t.resource.type=? and t.resource.parentId=?",
				new Object[]{roleId,new Long(1l),parentResourceId});		
		List menuResourceList = this.qryInList(qryBean);
		return menuResourceList;
	}
	
	public List getResourceListOfRole(Long roleId){
		QryBean qryBean = new QryBean(
				"select t.resource.id from RoleResource as t where t.role.id=?",
				new Object[]{roleId});		
		List list = this.qryInList(qryBean);
		List<String> resourceList = new ArrayList<String>();
		if(list!=null){
			for(Object o:list){
				resourceList.add(String.valueOf(o));
			}
		}
		return resourceList;
	}
}
