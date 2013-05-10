package com.eds.ctcb.dao.system;

import com.eds.ctcb.dao.BaseDao;

public interface LogDao  extends BaseDao{
	public void createLog(int type,long userId,String content);
}
