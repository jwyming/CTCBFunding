package com.eds.ctcb.dao.priv;

import java.util.List;

import com.eds.ctcb.dao.BaseDao;

public interface RoleResourceDao  extends BaseDao{
	public List getMenuListOfRole(Long roleId,Long parentResourceId);
	public List getResourceListOfRole(Long roleId);
}
