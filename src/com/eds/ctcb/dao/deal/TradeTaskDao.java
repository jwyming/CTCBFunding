package com.eds.ctcb.dao.deal;

import java.util.List;

import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.TradeTask;

public interface TradeTaskDao  extends BaseDao{
	public List<TradeTask> getUnexecutedTasks();
}
