package com.eds.ctcb.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TradeBean extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String accountBySaccountId;

	private String accountByDaccountId;

	private Integer type;

	private Integer tradeMode;

	private BigDecimal count;

	private Date createTime;

	private Date setTime;

	public String getAccountByDaccountId() {
		return accountByDaccountId;
	}

	public void setAccountByDaccountId(String accountByDaccountId) {
		this.accountByDaccountId = accountByDaccountId;
	}

	public String getAccountBySaccountId() {
		return accountBySaccountId;
	}

	public void setAccountBySaccountId(String accountBySaccountId) {
		this.accountBySaccountId = accountBySaccountId;
	}

	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getSetTime() {
		return setTime;
	}

	public void setSetTime(Date setTime) {
		this.setTime = setTime;
	}

	public Integer getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @param id
	 * @param accountBySaccountId
	 * @param accountByDaccountId
	 * @param type
	 * @param tradeMode
	 * @param count
	 * @param createTime
	 * @param setTime
	 */
	public TradeBean(Long id, String accountBySaccountId, String accountByDaccountId, Integer type, Integer tradeMode, BigDecimal count, Date createTime, Date setTime) {
		super();
		this.id = id;
		this.accountBySaccountId = accountBySaccountId;
		this.accountByDaccountId = accountByDaccountId;
		this.type = type;
		this.tradeMode = tradeMode;
		this.count = count;
		this.createTime = createTime;
		this.setTime = setTime;
	}

	/**
	 * 
	 */
	public TradeBean() {
		
	}

}
