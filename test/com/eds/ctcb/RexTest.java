package com.eds.ctcb;

import com.eds.ctcb.util.DataUtil;

public class RexTest {
	public static void main(String[] args) 
	{
//		Set <FundPerformance> performanceSet=new TreeSet<FundPerformance>();
//       
//        for(int i=20;i>0;i--){
//        	
//        	
//        	FundPerformance fundPerformance=new FundPerformance("基金","基金","基金","基金","基金","基金",new BigDecimal(1),new BigDecimal(1),"基金",new BigDecimal(i));
//        	performanceSet.add(fundPerformance);
//        }
//        performanceSet.add(new FundPerformance("基金11","基金11","基金11","基金11","基金11","基金11",new BigDecimal(1),new BigDecimal(1),"基金",new BigDecimal(11)));
//        Object [] sortedArray=performanceSet.toArray();
//        ArrayList <FundPerformance> resultArray=new ArrayList<FundPerformance>(10);
//        int count=sortedArray.length;
//        System.out.println(count);
//        if(count>20){
//        	for(int i=1;i<=20;i++){
//        	resultArray.add((FundPerformance)sortedArray[count-i]);
//        	}
//        }else{
//        	for(int i=1;i<=count;i++){
//        	resultArray.add((FundPerformance)sortedArray[count-i]);
//        	}
//        }
//      
//        ListIterator resultListIt=resultArray.listIterator();
//		List <FundPerformance>resultList=new LinkedList<FundPerformance>();
//		while(resultListIt.hasNext()){
//			resultList.add((FundPerformance)resultListIt.next());
//		}
//		
//		
//		Iterator it=resultList.iterator();
//		while(it.hasNext()){
//			FundPerformance temp=(FundPerformance)it.next();
//			System.out.println(temp.getFundCode()+"-----"+temp.getPerformanceValue());
//		}
//		
//		
//		//test compute day
//		SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");
//		Calendar c=Calendar.getInstance();
//		System.out.println(df.format(c.getTime()));
//		int day=c.get(Calendar.DAY_OF_YEAR);
//		day=day-360;
//		c.set(Calendar.DAY_OF_YEAR, day);
//		System.out.println(df.format(c.getTime()));
		
		System.out.println(DataUtil.Float2Percent(new Float(-0.0f)));
	}
}
