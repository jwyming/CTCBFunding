package com.eds.ctcb.dao.priv;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.Role;

public interface RoleDao extends BaseDao{
	public Role getRoleById(Long Id);
	public Role getRoleByName(String name);

}
