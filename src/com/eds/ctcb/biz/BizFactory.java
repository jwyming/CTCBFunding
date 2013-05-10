package com.eds.ctcb.biz;



import com.eds.ctcb.biz.deal.ChangeFundBiz;
import com.eds.ctcb.biz.deal.InvestmentBiz;
import com.eds.ctcb.biz.deal.InvestmentChangeBiz;
import com.eds.ctcb.biz.deal.IrregularInvestmentBiz;
import com.eds.ctcb.biz.deal.RegularInvestmentBiz;
import com.eds.ctcb.biz.deal.SellingBiz;
import com.eds.ctcb.biz.deal.SingleInvestmentBiz;
import com.eds.ctcb.biz.deal.SwitchInvestmentBiz;
import com.eds.ctcb.biz.deal.exec.InvestmentExecBiz;
import com.eds.ctcb.biz.deal.exec.SavingPlanExecBiz;
import com.eds.ctcb.biz.priv.PrivBiz;
import com.eds.ctcb.biz.report.ReportBiz;

import com.eds.ctcb.biz.system.SystemManagementBiz;
import com.eds.ctcb.biz.system.UserBiz;
import com.eds.ctcb.biz.task.TimerTaskBiz;
import com.eds.ctcb.util.AppContextUtil;


public class BizFactory {
	public static BizFactory instance;
	
	private BizFactory(){};
	
	public static BizFactory getInstance(){
		if(instance == null){
//            System.out.println("1111111111111111");
            instance = (BizFactory)(AppContextUtil.getApplicationContext().getBean("bizFactory"));
//            System.out.println("2222222222222222");
//            System.out.println("333333333333333333");
        }
		return instance;
	}	
	
	
	
	private PrivBiz privBiz;
	private InvestmentBiz investmentBiz;
	private SingleInvestmentBiz singleInvestmentBiz;
	private SystemManagementBiz systemManagementBiz;
	private RegularInvestmentBiz regularInvestmentBiz;
	private IrregularInvestmentBiz irregularInvestmentBiz;
	private SellingBiz sellingBiz;
	private ChangeFundBiz changeFundBiz;
	private SwitchInvestmentBiz switchInvestmentBiz;
	private InvestmentChangeBiz investmentChangeBiz;
	private UserBiz userBiz;
	private ReportBiz reportBiz;
	private InvestmentExecBiz investmentExecBiz;
	private SavingPlanExecBiz savingPlanExecBiz;
    private TimerTaskBiz timerTaskBiz;




	public InvestmentChangeBiz getInvestmentChangeBiz() {
		return investmentChangeBiz;
	}

	public void setInvestmentChangeBiz(InvestmentChangeBiz investmentChangeBiz) {
		this.investmentChangeBiz = investmentChangeBiz;
	}

	public ChangeFundBiz getChangeFundBiz() {
		return changeFundBiz;
	}

	public void setChangeFundBiz(ChangeFundBiz changeFundBiz) {
		this.changeFundBiz = changeFundBiz;
	}

	public SwitchInvestmentBiz getSwitchInvestmentBiz() {
		return switchInvestmentBiz;
	}

	public void setSwitchInvestmentBiz(SwitchInvestmentBiz switchInvestmentBiz) {
		this.switchInvestmentBiz = switchInvestmentBiz;
	}

	public SellingBiz getSellingBiz() {
		return sellingBiz;
	}

	public void setSellingBiz(SellingBiz sellingBiz) {
		this.sellingBiz = sellingBiz;
	}

	public ReportBiz getReportBiz() {
		return reportBiz;
	}

	public void setReportBiz(ReportBiz reportBiz) {
		this.reportBiz = reportBiz;
	}

	public PrivBiz getPrivBiz() {
		return privBiz;
	}
	public void setPrivBiz(PrivBiz privBiz) {
		this.privBiz = privBiz;
	}

	public InvestmentBiz getInvestmentBiz() {
		return investmentBiz;
	}

	public void setInvestmentBiz(InvestmentBiz investmentBiz) {
		this.investmentBiz = investmentBiz;
	}

	public SingleInvestmentBiz getSingleInvestmentBiz() {
		return singleInvestmentBiz;
	}

	public void setSingleInvestmentBiz(SingleInvestmentBiz singleInvestmentBiz) {
		this.singleInvestmentBiz = singleInvestmentBiz;
	}

	public SystemManagementBiz getSystemManagementBiz() {
		return systemManagementBiz;
	}

	public void setSystemManagementBiz(SystemManagementBiz systemManagementBiz) {
		this.systemManagementBiz = systemManagementBiz;
	}


	public UserBiz getUserBiz() {
		return userBiz;
	}

	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}

public InvestmentExecBiz getInvestmentExecBiz() {
	return investmentExecBiz;
}

public void setInvestmentExecBiz(InvestmentExecBiz investmentExecBiz) {
	this.investmentExecBiz = investmentExecBiz;
}

public SavingPlanExecBiz getSavingPlanExecBiz() {
	return savingPlanExecBiz;
}

public void setSavingPlanExecBiz(SavingPlanExecBiz savingPlanExecBiz) {
	this.savingPlanExecBiz = savingPlanExecBiz;
}

public RegularInvestmentBiz getRegularInvestmentBiz() {
	return regularInvestmentBiz;
}

public void setRegularInvestmentBiz(RegularInvestmentBiz regularInvestmentBiz) {
	this.regularInvestmentBiz = regularInvestmentBiz;
}

public IrregularInvestmentBiz getIrregularInvestmentBiz() {
	return irregularInvestmentBiz;
}

public void setIrregularInvestmentBiz(
		IrregularInvestmentBiz irregularInvestmentBiz) {
	this.irregularInvestmentBiz = irregularInvestmentBiz;
}


    public TimerTaskBiz getTimerTaskBiz() {
        return timerTaskBiz;
    }

    public void setTimerTaskBiz(TimerTaskBiz timerTaskBiz) {
        this.timerTaskBiz = timerTaskBiz;
    }
}
