package com.eds.ctcb.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import com.eds.ctcb.constant.CompetitionTopicType;
import com.eds.ctcb.constant.QuarterType;
import com.eds.ctcb.constant.TradeMode;
import com.eds.ctcb.constant.TradeType;
import com.eds.ctcb.constant.UserSex;
import com.eds.ctcb.constant.UserStatus;
import com.eds.ctcb.constant.YearType;


public class OptionsUtil {
	public final static HashMap<String,HashMap> optionsMap = new HashMap<String,HashMap>();
	
	static{
		HashMap<String,String> map = null;
		/*=========================== UserStatus ================================*/
		map = new LinkedHashMap<String,String>();
		map.put(String.valueOf(UserStatus.INIT), "Options.UserStatus.INIT");
		map.put(String.valueOf(UserStatus.NORMAL), "Options.UserStatus.NORMAL");
		map.put(String.valueOf(UserStatus.DELETED), "Options.UserStatus.DELETED");
		optionsMap.put(UserStatus.class.getSimpleName().toUpperCase(), map);
		/*=========================== UserSex ================================*/
		map = new LinkedHashMap<String,String>();
		map.put(String.valueOf(UserSex.MALE), "Options.UserSex.MALE");
		map.put(String.valueOf(UserSex.FEMALE), "Options.UserSex.FEMALE");
		optionsMap.put(UserSex.class.getSimpleName().toUpperCase(), map);
		/*===========================Taiwan Year ================================*/
		map = new LinkedHashMap<String,String>();
		map.put(String.valueOf(YearType.Y2007), "Options.Year.2007");
		map.put(String.valueOf(YearType.Y2008), "Options.Year.2008");
		map.put(String.valueOf(YearType.Y2009), "Options.Year.2009");
		map.put(String.valueOf(YearType.Y2010), "Options.Year.2010");
		map.put(String.valueOf(YearType.Y2011), "Options.Year.2011");
		map.put(String.valueOf(YearType.Y2012), "Options.Year.2012");
		map.put(String.valueOf(YearType.Y2013), "Options.Year.2013");
		map.put(String.valueOf(YearType.Y2014), "Options.Year.2014");
		map.put(String.valueOf(YearType.Y2015), "Options.Year.2015");
		map.put(String.valueOf(YearType.Y2016), "Options.Year.2016");
		map.put(String.valueOf(YearType.Y2017), "Options.Year.2017");
		optionsMap.put(YearType.class.getSimpleName().toUpperCase(), map);
		
		/*===========================Quarter ================================*/
		map = new LinkedHashMap<String,String>();
		map.put(String.valueOf(QuarterType.QTR1ST), "Options.Quarter.1st");
		map.put(String.valueOf(QuarterType.QTR2ND), "Options.Quarter.2nd");
		map.put(String.valueOf(QuarterType.QTR3RD), "Options.Quarter.3rd");
		map.put(String.valueOf(QuarterType.QTR4TH), "Options.Quarter.4th");
		optionsMap.put(QuarterType.class.getSimpleName().toUpperCase(), map);
		/*===========================Topic ================================*/
		map = new LinkedHashMap<String,String>();
		map.put(String.valueOf(CompetitionTopicType.PERFORMANCE_KING), "Options.TopicType.performance");
		map.put(String.valueOf(CompetitionTopicType.ASSET_CONFIGURATION_KING), "Options.TopicType.asset");
		map.put(String.valueOf(CompetitionTopicType.SAVING_PLAN_KING), "Options.TopicType.saving");
		optionsMap.put(CompetitionTopicType.class.getSimpleName().toUpperCase(), map);
		/*=====================tradeType=================================*/
		 HashMap <Integer,String> tradeTypeMap =new LinkedHashMap<Integer, String>();
	     tradeTypeMap.put(TradeType.CHANGE_FUND, "trade.type.changeFund");
	     tradeTypeMap.put(TradeType.IRREGULAR_INVESTMENT, "trade.type.irregularInvestment");
	     tradeTypeMap.put(TradeType.REGULAR_INVESTMENT, "trade.type.regularInvestment");
	     tradeTypeMap.put(TradeType.SELLING, "trade.type.selling");
	     tradeTypeMap.put(TradeType.SINGLE_INVESTMENT, "trade.type.singleInvestment");
	     tradeTypeMap.put(TradeType.SWITCH_INVESTMENT, "trade.type.switchInvestment");	         
	     optionsMap.put(TradeType.class.getSimpleName().toUpperCase(), tradeTypeMap);
	     /*=============================tradeMode=========================*/
	     HashMap <Integer,String>  tradeModeMap =new LinkedHashMap<Integer, String>();
	     tradeModeMap.put(TradeMode.MONEY, "trade.tradeMode.money");
	     tradeModeMap.put(TradeMode.UNIT, "trade.tradeMode.unit");
	     optionsMap.put(TradeMode.class.getSimpleName().toUpperCase(), tradeModeMap);
	     /*================================competitiontopicMap===============================*/
	     map = new LinkedHashMap<String,String>();
	     map.put(CompetitionTopicType.PERFORMANCE_KING, "competitionTopic.performanceKing");
	     map.put(CompetitionTopicType.ASSET_CONFIGURATION_KING, "competitionTopic.assetConfigurationKing");
	     map.put(CompetitionTopicType.SAVING_PLAN_KING, "competitionTopic.savingKing");
	     optionsMap.put(CompetitionTopicType.class.getSimpleName().toUpperCase(), map);
	     
	     
	}	
	
	public static HashMap getOptionsMap(String mapName,ResourceBundle rb){
		HashMap<Object,String> newMap = new LinkedHashMap<Object,String>();
		if(! DataUtil.isEmptyStr(mapName)){
			HashMap oldMap = (HashMap)(optionsMap.get(mapName.toUpperCase()));
			if(oldMap!=null){
				Iterator i = oldMap.keySet().iterator();
				while(i.hasNext()){
					Object key = i.next();
					String i18nkey = (String)(oldMap.get(key));
					String i18n = "";
					try{
						i18n = rb.getString(i18nkey);
					}catch(Exception e){
						e.printStackTrace();
					}
					newMap.put(key, i18n);
				}
			}
		}

		
		return newMap;
	};
}
