package com.eds.ctcb.biz;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public abstract class BaseBizTest extends AbstractTransactionalDataSourceSpringContextTests{
	 protected String[] getConfigLocations() { 
		   return new String[] {"applicationContext.xml"}; 
		 }

}
