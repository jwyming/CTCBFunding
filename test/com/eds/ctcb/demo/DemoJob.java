package com.eds.ctcb.demo;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DemoJob extends QuartzJobBean {
	  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//		  System.out.println("Quartz task scheduling: Hello!");
	  }

}
