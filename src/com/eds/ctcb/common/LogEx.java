package com.eds.ctcb.common;

import org.apache.log4j.Logger;

public class LogEx 
{
    private org.apache.log4j.Logger logger;    

    public LogEx(Class _class)
    {
        this.logger = Logger.getLogger(_class);
    }
    
    public void info(Object obj){
        this.logger.info(obj);
    }
    
    public void debug(Object obj){
        this.logger.debug(obj);
    }
    
    public void warn(Object obj){
        this.logger.warn(obj);
    }
    
    public void error(Object obj){
        this.logger.error(obj);
    }
    
    public void methodBegin(String methodName){
        this.logger.info("=============== Before Method : "+methodName);
    }
    
    public void methodEnd(String methodName){
        this.logger.info("=============== After Method : "+methodName);
    }
    
    public void methodParam(Object param){
        this.logger.info("----- [Param]  = "+param);
    }
    
    public void methodResult(Object result){
        this.logger.info("----- [Result] = "+result);
    }
    
    
    public void enterAction(String actionName){
        this.logger.info("Entering Action : "+actionName+"...........");
    }    
    

   
    

    
//    public static String toStr(String[] strArray){
//        StringBuffer sb = new StringBuffer();
//        if(strArray!=null){
//            for(int i=0;i<strArray.length;i++){
//                sb.append("\n"+strArray[i]);
//            }
//        }
//        return sb.toString();
//    }
    
}
