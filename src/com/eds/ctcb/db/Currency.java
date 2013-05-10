package com.eds.ctcb.db;
// default package



/**
 * Fund generated by MyEclipse - Hibernate Tools
 */

public class Currency  implements java.io.Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields    

	
     private Long id;
     private String name;
     private Integer type;
     private String remark;

    // Constructors

    /**
	 * @param name
	 * @param type
	 * @param remark
	 */
	public Currency(String name, Integer type, String remark) {
		super();
		this.name = name;
		this.type = type;
		this.remark = remark;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/** default constructor */
    public Currency() {
    }

	/** minimal constructor */
    public Currency(String name) {
        this.name = name;
    }
    
    /** full constructor */
    public Currency(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}