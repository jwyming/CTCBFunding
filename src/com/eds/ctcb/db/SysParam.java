package com.eds.ctcb.db;

// default package

/**
 * SysParam generated by MyEclipse - Hibernate Tools
 */

public class SysParam implements java.io.Serializable {

	// Fields

	private Long id;

	private String value;

	private String name;

	private String remark;

	// Constructors

	/** default constructor */
	public SysParam() {
	}

	/** minimal constructor */
	public SysParam(String value, String name) {
		this.value = value;
		this.name = name;
	}

	/** full constructor */
	public SysParam(String value, String name, String remark) {
		this.value = value;
		this.name = name;
		this.remark = remark;
	}

	public SysParam(Long id, String value, String name, String remark) {
		this.id = id;
		this.value = value;
		this.name = name;
		this.remark = remark;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}