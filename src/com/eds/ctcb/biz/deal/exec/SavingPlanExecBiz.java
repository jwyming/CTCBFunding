package com.eds.ctcb.biz.deal.exec;

import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.db.SavingPlan;

import java.util.List;

public interface SavingPlanExecBiz {

    public List<SavingPlan> getUnexecutedSavingPlans();
    public boolean executeSavingPlan(SavingPlan savingPlan) throws BizException;
}
