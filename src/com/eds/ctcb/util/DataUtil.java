package com.eds.ctcb.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import sun.misc.BASE64Encoder;

public class DataUtil {
    private final static String DATE_FORMATE="yyyy/MM/dd";
    private final static String DATETIME_FORMATE="yyyy/MM/dd HH:mm:ss";    
	
	
	
	public static String getRegularStr(String s){
		if(s == null){
			s = "";
		}
		s.trim();
		return s;
	}
	
	public static boolean isStrEqual(String s1,String s2){
		s1 = DataUtil.getRegularStr(s1);
		s2 = DataUtil.getRegularStr(s2);
		return s1.equals(s2);
	}
	
	public static boolean isEmptyStr(String s){
		if(s == null || s.trim().equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	
	  public static String getEncodedStr(String str)  {
		  String newstr = str;
	          try {
				MessageDigest md5=MessageDigest.getInstance("MD5");
				BASE64Encoder base64en = new BASE64Encoder();
				newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          return newstr;
	     }  
	  
 
	   public static String bean2String(Object obj){
		   StringBuffer sb = new StringBuffer();
		   sb.append("["+obj.getClass().getSimpleName()+"] ");
//	        Field[] allField = obj.getClass().getDeclaredFields(); 
	        Method[] allMethod = obj.getClass().getDeclaredMethods();	        
	        Method[] allMethod2 = null;
	        Method[] allMethod3 = null;
	        if (obj.getClass().getSuperclass() != null) {
	            allMethod2 = obj.getClass().getSuperclass().getDeclaredMethods();
	            if (obj.getClass().getSuperclass().getSuperclass() != null) {
	                allMethod3 = obj.getClass().getSuperclass().getSuperclass().getDeclaredMethods();
	            }
	        }

	            try {
	                for (int i = 0; i < allMethod.length; i++) {
	                    Method method = allMethod[i];
	                    sb.append(beanField2String(obj,method));
	                }
	                if(allMethod2 != null){
	                    for (int i = 0; i < allMethod2.length; i++) {
	                        Method method = allMethod2[i];
	                        sb.append(beanField2String(obj,method));
	                    }
	                    if (allMethod3 != null) {
	                        for (int i = 0; i < allMethod3.length; i++) {
	                            Method method = allMethod3[i];
	    	                    sb.append(beanField2String(obj,method));
	                        }
	                    }
	                }

	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	        return sb.toString();
	    } 
	   
	   static private String beanField2String(Object obj,Method method){
		   if(method == null){
			   return "";
		   }else if(!method.getName().startsWith("get")){
			   return "";
		   }else if(method.getParameterTypes()!=null && method.getParameterTypes().length>0){
			   return "";
		   }else{
			   Object result = null;
				try {
					result = method.invoke(obj, new Object[0]);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			   return method.getName().substring(3)+"="+result+";";
		   }
	   }
	  
	  
	   
	    public static Date str2Date(String s,boolean includeTime){
	    	if(DataUtil.isEmptyStr(s)){
	    		return null;
	    	}
	    	
	        //////////////////	    	
	    	String[] sArray = s.split("/");	    	
	        String s1=sArray[0];
	        String s2=s.substring(s1.length());
	        int year = Integer.parseInt(s1);
	        year = year + 1911;
	        s = String.valueOf(year)+s2;
	        ////////////////
	        Date d = null;
	        String df = DATE_FORMATE;
	        if(includeTime == true){
	        	df = DATETIME_FORMATE;
	        }
	        SimpleDateFormat sdf = new SimpleDateFormat(df);
	        sdf.setLenient(false);

	        try{
	            d = sdf.parse(s);
	        }catch(Exception e){
	            
	        } 

	        return d;
	        
	    }    
	    
	    public static String date2Str(Date d,boolean includeTime){
	    	 if(d == null){
		            return "   ";
	    	 }
	    	 
	        String df = DATE_FORMATE;
	        if(includeTime == true){
	        	df = DATETIME_FORMATE;
	        }
	        
	        String s = " ";
      
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat(df);
            s  = sdf.format(d);
  
	        //////////////////
	        String s1=s.substring(0, 4);
	        String s2=s.substring(4);
	        int year = Integer.parseInt(s1);
	        year = year -1911;
	        s = String.valueOf(year)+s2;
	        ////////////////
	        return s;    
	    }    
	    public static void main(String[] args){
	    	Date d = new Date();
	    	String s1 = DataUtil.date2Str(d, true);
	    	String s2 = DataUtil.date2Str(d, false);
//	    	System.out.println(s1);
//	    	System.out.println(s2);
	    	Date d1 = DataUtil.str2Date("96/3/5 10:00:00", true);
	    	Date d2 = DataUtil.str2Date("-2/3/5", false);
	    	String s3 = DataUtil.date2Str(d1, true);
	    	String s4 = DataUtil.date2Str(d2, false);
//	    	System.out.println(s3);
//	    	System.out.println(s4);
	    }
	    
	    
	    public static int getByteNumOfStr(String s){
	        int n=0;
	        if(s==null){
	            n=-1;
	        }else if(s.equals("")){
	            n=0;
	        }else{
	            byte[] bytes = s.getBytes();
	            n = bytes.length;
	        }
	        
	        return n;
	    }
	    public static Integer date2Int(Date x){
			String sdate = date2Str(x, false);
			String temp = "";
			String[] sdateArray = sdate.split("/");
			for(int i = 0; i<sdateArray.length; i++){
				temp = temp + sdateArray[i];
			}
			Integer idate = Integer.parseInt(temp);
			return idate;
			
		}
	    
	    public static String Float2Percent(Float value){
			BigDecimal tempValue=new BigDecimal(value);
			BigDecimal result;
			result=tempValue.movePointRight(2);
			result=result.setScale(2, BigDecimal.ROUND_HALF_DOWN);
			return result.toString()+"%";
		}
	    
		public static String formatBigDecimal(BigDecimal d){   
            String str="";   
            DecimalFormat df=new DecimalFormat("00000000000000000000.00");
            str=df.format(d);   
            return   str;   
  
    }
}
