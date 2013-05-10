package com.eds.ctcb.biz.deal.exec;

import com.eds.ctcb.biz.BizFactory;
import com.eds.ctcb.db.TradeTask;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.List;

public class InvestmentExecBizTest extends TestCase {
    protected ClassPathXmlApplicationContext context = null;
    protected SessionFactory sessionFactory = null;
    private InvestmentExecBiz investmentExecBiz;

    protected void setUp() throws Exception {

        super.setUp();
         String contextLocation = "applicationContext.xml";
         context = new ClassPathXmlApplicationContext(contextLocation);
         sessionFactory = (SessionFactory) context.getBean("sessionFactory");
         Session session = SessionFactoryUtils.getSession(sessionFactory, true);
         TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        BizFactory bizFactory = (BizFactory) context.getBean("bizFactory");
        investmentExecBiz = bizFactory.getInvestmentExecBiz();
    }

    public void testGetUnexecutedTasks() {
        List<TradeTask> unexecutedTasks = investmentExecBiz.getUnexecutedTasks();
        assertFalse(unexecutedTasks.size() == 0);
    }

    public void testCanExecutedNow() {
        List<TradeTask> unexecutedTasks = investmentExecBiz.getUnexecutedTasks();
        for(TradeTask tradeTask : unexecutedTasks) {
            boolean can = investmentExecBiz.canExecutedNow(tradeTask);
        }
    }

    public void testExecuteTradeTask() throws Exception {
        List<TradeTask> unexecutedTasks = investmentExecBiz.getUnexecutedTasks();
        for(TradeTask tradeTask : unexecutedTasks) {
//            if(investmentExecBiz.canExecutedNow(tradeTask)) {
                investmentExecBiz.executeTradeTask(tradeTask);
//            }
        }
    }

    protected void tearDown() throws Exception {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
        SessionFactoryUtils.releaseSession(sessionHolder.getSession(), sessionFactory);
        context = null;
        super.tearDown();
    }
}