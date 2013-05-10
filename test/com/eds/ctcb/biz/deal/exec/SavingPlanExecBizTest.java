package com.eds.ctcb.biz.deal.exec;

import com.eds.ctcb.biz.BizFactory;
import com.eds.ctcb.db.SavingPlan;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

public class SavingPlanExecBizTest extends TestCase {
    protected ClassPathXmlApplicationContext context = null;
    protected SessionFactory sessionFactory = null;
    private SavingPlanExecBiz savingPlanExecBiz;

    protected void setUp() throws Exception {
        super.setUp();
         String contextLocation = "applicationContext.xml";
         context = new ClassPathXmlApplicationContext(contextLocation);
         sessionFactory = (SessionFactory) context.getBean("sessionFactory");
         Session session = SessionFactoryUtils.getSession(sessionFactory, true);
         TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
        BizFactory bizFactory = (BizFactory) context.getBean("bizFactory");
        savingPlanExecBiz = bizFactory.getSavingPlanExecBiz();
    }

    public void testGetAllValidSavingPlans() throws Exception {
        List<SavingPlan> savingPlans = savingPlanExecBiz.getUnexecutedSavingPlans();
        assertTrue(savingPlans.size() > 0);
    }

    public void testExecuteSavingPlan() throws Exception {
        List<SavingPlan> savingPlans = savingPlanExecBiz.getUnexecutedSavingPlans();
        for(SavingPlan savingPlan : savingPlans) {
            boolean result = savingPlanExecBiz.executeSavingPlan(savingPlan);
        }
    }

    protected void tearDown() throws Exception {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
        SessionFactoryUtils.releaseSession(sessionHolder.getSession(), sessionFactory);
        context = null;
        super.tearDown();
    }
}