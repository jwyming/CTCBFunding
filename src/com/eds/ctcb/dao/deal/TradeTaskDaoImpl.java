package com.eds.ctcb.dao.deal;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.constant.AccountType;
import com.eds.ctcb.constant.Global;
import com.eds.ctcb.constant.TradeTaskStatus;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.Account;
import com.eds.ctcb.db.TradeTask;

import java.util.List;

public class TradeTaskDaoImpl extends BaseDaoImpl implements TradeTaskDao {

	public List<TradeTask> getUnexecutedTasks() {
		String hql = "from TradeTask as tt where (tt.status = ? or tt.status = ?) and trunc(tt.planTime) <= trunc(?) and tt.count <= ? ";
		QryBean qryBean = new QryBean(hql, new Object[]{TradeTaskStatus.INITIAL,
				TradeTaskStatus.FAILED,
				this.getSysdate(),
				Global.TASK_LIMIT_TIMES});
		List list = this.qryInList(qryBean);
        for (Object aList : list) {
            TradeTask tradeTask = (TradeTask) aList;
            Account sourceAccount = tradeTask.getTrade().getAccountBySaccountId();
            int sourceAccountType = sourceAccount.getType();
            if(sourceAccountType == AccountType.FROZEN_FUND || sourceAccountType == AccountType.NON_FROZEN_FUND) {
                sourceAccount.getFundAccount().getFund().getArea().getId();
                sourceAccount.getFundAccount().getFund().getCompany().getId();
                sourceAccount.getFundAccount().getFund().getCurrency().getId();
                sourceAccount.getFundAccount().getAccount().getUser().getAccounts().size();
            } else if(sourceAccountType == AccountType.FROZEN_CASH || sourceAccountType == AccountType.NON_FROZEN_CASH) {
                sourceAccount.getCashAccount().getCurrency().getId();
                sourceAccount.getCashAccount().getAccount().getUser().getAccounts().size();
            }

            Account destinationAccount = tradeTask.getTrade().getAccountByDaccountId();
            int destinationAccountType = destinationAccount.getType();
            if(destinationAccountType == AccountType.FROZEN_FUND || destinationAccountType == AccountType.NON_FROZEN_FUND) {
                destinationAccount.getFundAccount().getFund().getArea().getId();
                destinationAccount.getFundAccount().getFund().getCompany().getId();
                destinationAccount.getFundAccount().getFund().getCurrency().getId();
                destinationAccount.getFundAccount().getAccount().getUser().getAccounts().size();
            } else if(destinationAccountType == AccountType.FROZEN_CASH || destinationAccountType == AccountType.NON_FROZEN_CASH) {
                destinationAccount.getCashAccount().getCurrency();
                destinationAccount.getCashAccount().getAccount().getUser().getAccounts().size();
            }
        }
        return list;
    }
}
