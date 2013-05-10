package com.eds.ctcb.dao.system;

import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.Log;
import com.eds.ctcb.db.User;

public class LogDaoImpl extends BaseDaoImpl implements LogDao {
	public void createLog(int type,long userId,String content){
		Log log = new Log();
		log.setContent(content);
		User user = new User();
		user.setId(userId);
		log.setUser(user);
		log.setType(type);
		log.setTime(this.getSysdate());
		this.create(log);
	}
}
