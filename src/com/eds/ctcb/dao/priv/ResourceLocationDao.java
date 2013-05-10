package com.eds.ctcb.dao.priv;

import java.util.List;

import com.eds.ctcb.dao.BaseDao;

public interface ResourceLocationDao  extends BaseDao{
	public List<String> getAccessableUrlList4Role(Long roleId);
	
}
