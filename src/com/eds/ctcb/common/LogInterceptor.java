package com.eds.ctcb.common;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogInterceptor implements MethodInterceptor {
	private static LogEx log = new LogEx(LogInterceptor.class);
	public Object invoke(MethodInvocation mi) throws Throwable {
		String methodName = mi.getMethod().getDeclaringClass().getSimpleName()+"."+mi.getMethod().getName()+"()";
		Object[] args = mi.getArguments();
		Object result = null;
		log.methodBegin(methodName);
		if(args!=null){
			for(Object arg : args){
				log.methodParam(arg);
			}
		}
		try{
			result = mi.proceed();
		}finally{
			log.methodResult(result);
			log.methodEnd(methodName);
		}
		
		return result;
	}

}
