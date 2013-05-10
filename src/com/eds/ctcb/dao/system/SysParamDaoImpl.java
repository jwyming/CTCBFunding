package com.eds.ctcb.dao.system;


import java.math.BigDecimal;
import java.util.List;

import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.constant.SysParameter;
import com.eds.ctcb.dao.BaseDaoImpl;
import com.eds.ctcb.db.Role;
import com.eds.ctcb.db.SysParam;
import com.eds.ctcb.exception.BizException;
import com.eds.ctcb.exception.BizExceptionCode;

public class SysParamDaoImpl extends BaseDaoImpl implements SysParamDao {
	
	    public void createSysParam(SysParam sysParam) {
	      this.create(sysParam);
	    }
	    /*
	     * (non-Javadoc)
	     * @see com.eds.ctcb.dao.system.SysParamDao#updateSysParam(com.eds.ctcb.db.SysParam)
	     * when one parameter  already exists,it must be updated,since in SysParam table
	     * name is defined unique,cann't directly use hibernate upadate() method,
	     * thus we firstly delete such record,then create again;
	     * if one parameter never exist it must be created
	     * 
	     */
	  
	    public void updateSysParam(SysParam sysParam) {
	    	SysParam sp=this.findSysParamByName(sysParam.getName());
	    	if(sp!=null){
	    		sp.setValue(sysParam.getValue());
	    		sp.setRemark(sysParam.getRemark());
	    		this.update(sp);
	    	}
	    	else
	    		this.create(sysParam);
	    }
	   
	    public void deleteSysParam(SysParam sysParam){
	    	this.delete(sysParam);
	    }
	  
	    public SysParam findSysParamByName( String name) {
	        SysParam  sysParam = null ;
	        String hql="from SysParam as sysParam where sysParam.name=?";
	        QryBean qryBean=new QryBean(hql,new String[]{name});
	        List tempList = this.qryInList(qryBean);
			if(tempList!=null && tempList.size()==1 && tempList.get(0) instanceof SysParam){
				sysParam =(SysParam)(tempList.get(0));
			}
	        return sysParam;
	    }
	    
	    public String getInitPwd(){
	    	SysParam sysParam=this.findSysParamByName(SysParameter.INIT_PASSWORD);
	    	return sysParam.getValue();
	    }
	    
	   
	    public BigDecimal getInitInvAmount(){
			SysParam sysParam=this.findSysParamByName(SysParameter.INIT_INV_AMOUNT);
			return new BigDecimal(sysParam.getValue());
		
		}
	    
		public BigDecimal getLowestHandleTariff(){
			SysParam sysParam=this.findSysParamByName(SysParameter.LOWEST_HANDLE_TARIFF);
			return new BigDecimal(sysParam.getValue());
			
		}
		
		
		public BigDecimal getHighestHandleTariff(){
			SysParam sysParam=this.findSysParamByName(SysParameter.HIGHEST_HANDLE_TARIFF);
			return new BigDecimal(sysParam.getValue());
			
		}
		
		public BigDecimal getHandleRate(){
			SysParam sysParam=this.findSysParamByName(SysParameter.HANDLE_RATE);
			return new BigDecimal(sysParam.getValue());
		}
		
		public BigDecimal getLowestTransferTariff(){
			SysParam sysParam=this.findSysParamByName(SysParameter.LOWEST_TRANSFER_TARIFF);
			return new BigDecimal(sysParam.getValue());
			
		}
		
		public BigDecimal getHighestTransferTariff(){
			SysParam sysParam=this.findSysParamByName(SysParameter.HIGHEST_TRANSFER_TARIFF);
			return new BigDecimal(sysParam.getValue());
			
		}
		
		public BigDecimal getTransferRate(){
			SysParam sysParam=this.findSysParamByName(SysParameter.TRANSFER_RATE);
			return new BigDecimal(sysParam.getValue());
		}
		
		public BigDecimal getMinTransAmt(){
			SysParam sysParam=this.findSysParamByName(SysParameter.MIN_TRANS_AMT);
			return new BigDecimal(sysParam.getValue());
		}
		
		public BigDecimal getMinFundUnit(){
			SysParam sysParam=this.findSysParamByName(SysParameter.MIN_FUND_UNIT);
			return new BigDecimal(sysParam.getValue());
		}
		
		
		
		public BigDecimal getHandlingTariff(BigDecimal amount) {
			// get lowestHandleTariff,highestHandleTariff,handleRate from DB

			BigDecimal lowestHandleTariff = this.getLowestHandleTariff();
			BigDecimal highestHandleTariff = this.getHighestHandleTariff();
			BigDecimal handleRate = this.getHandleRate();
			BigDecimal temp = amount.multiply(handleRate).divide(new BigDecimal("100"),10,BigDecimal.ROUND_HALF_UP);

			if (temp.compareTo(lowestHandleTariff) <= 0) {
				return lowestHandleTariff;
			}
			if (temp.compareTo(highestHandleTariff) >= 0) {
				return highestHandleTariff;
			}

			return temp;
		}
		
		public BigDecimal getTransferTariff(BigDecimal amount) {

			// get lowestTransferTariff/hightTransferTariff/transferRate fm DB

			BigDecimal lowestTransferTariff = this.getLowestTransferTariff();
			BigDecimal highestTransferTariff = this.getHighestTransferTariff();
			BigDecimal transferRate =this.getTransferRate();
			BigDecimal temp = amount.multiply(transferRate).divide(new BigDecimal("100"),10,BigDecimal.ROUND_HALF_UP);
			if (temp.compareTo(lowestTransferTariff) <= 0) {
				return lowestTransferTariff;
			}
			if (temp.compareTo(highestTransferTariff) >= 0) {
				return highestTransferTariff;
			}
			return temp;
		}

		/*
		 * while the inputing paramater is the mixedAmount(tranaction amount and
		 * transaction tariff) we can use this method get transaction amount from
		 * the mixed amount
		 * 
		 */
		public BigDecimal getHandlingTariffFromMixed(BigDecimal mixedAmount)
				throws BizException {

			BigDecimal lowestTariff = this.getLowestHandleTariff();
			BigDecimal highestTariff = this.getHighestHandleTariff();
			BigDecimal rate = this.getHandleRate().divide(new BigDecimal("100"),10,BigDecimal.ROUND_HALF_UP);

			// mixedAmount must be more than lowestTariff; "errCode","i18nMsg" to be
			// assaigned later
			if (mixedAmount.compareTo(lowestTariff) < 0)
				throw new BizException(BizExceptionCode.TTLAMT_LESSTHAN_MINHANDLETARIFF,"sysparam.error.amount", null, null);
			// when the mixedAmount reach the upper limit of tariff

			if (mixedAmount.compareTo((highestTariff.divide(rate, 10,
					BigDecimal.ROUND_HALF_UP)).add(highestTariff)) >= 0) {
				return highestTariff;
			}
			// when the mixedAmount less than the low limit
			if (mixedAmount.compareTo((lowestTariff.divide(rate, 10,
					BigDecimal.ROUND_HALF_UP)).add(lowestTariff)) <= 0) {
				return lowestTariff;
			}

			return mixedAmount.multiply(rate).divide(
					rate.add(new BigDecimal("1.0000")), 10, BigDecimal.ROUND_HALF_UP);
		}
		
		
}
