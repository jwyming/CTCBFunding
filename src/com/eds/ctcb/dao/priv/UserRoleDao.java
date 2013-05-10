package com.eds.ctcb.dao.priv;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.Role;

public interface UserRoleDao  extends BaseDao{
	public Role getRoleByUserId(Long userId);
}
