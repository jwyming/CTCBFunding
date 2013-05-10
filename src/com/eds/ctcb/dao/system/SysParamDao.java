package com.eds.ctcb.dao.system;

import java.math.BigDecimal;
import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.constant.SysParameter;
import com.eds.ctcb.dao.BaseDao;
import com.eds.ctcb.db.SysParam;
import com.eds.ctcb.exception.BizException;

public interface SysParamDao  extends BaseDao{
	
	
	  public void createSysParam(SysParam sysParam) ;
	    
	  public void updateSysParam(SysParam sysParam) ;
	   
	  public void deleteSysParam(SysParam sysParam);
	  
	  public SysParam findSysParamByName( String name) ;
	
		
	  public BigDecimal getLowestHandleTariff();
		
	  public BigDecimal getHighestHandleTariff();
		
	  public BigDecimal getHandleRate();
		
	  public BigDecimal getLowestTransferTariff();
		
	  public BigDecimal getHighestTransferTariff();
		
	  public BigDecimal getTransferRate();
	  
	  public BigDecimal getHandlingTariff(BigDecimal amount) ;
	  
	  public BigDecimal getTransferTariff(BigDecimal amount) ;

		/*
		 * while the inputing paramater is the mixedAmount(tranaction amount and
		 * transaction tariff) we can use this method get transaction amount from
		 * the mixed amount
		 * 
		 */
	 public BigDecimal getHandlingTariffFromMixed(BigDecimal mixedAmount)
				throws BizException;
		
	 public BigDecimal getInitInvAmount();
		 
	 public String getInitPwd();
		  
		 public BigDecimal getMinTransAmt();
		
			
		public BigDecimal getMinFundUnit();
}
