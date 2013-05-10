package com.eds.ctcb.dao;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public abstract class BaseDaoTest extends AbstractTransactionalDataSourceSpringContextTests{

	 protected String[] getConfigLocations() { 
		   return new String[] { "applicationContext.xml" }; 
		 } 
}
