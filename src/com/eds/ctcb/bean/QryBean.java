package com.eds.ctcb.bean;



public class QryBean {
	private String hql;
	private Object[] paramArray;
	
	public QryBean(String hql,Object[] paramArray){
		this.hql = hql;
		this.paramArray = paramArray;
	}
	
	public String getHql() {
		return hql;
	}
	public void setHql(String hql) {
		this.hql = hql;
	}
	public Object[] getParamArray() {
		return paramArray;
	}
	public void setParamArray(Object[] paramArray) {
		this.paramArray = paramArray;
	}
	
	public String toString(){
		//return DataUtil.bean2String(this);
		StringBuffer sb = new StringBuffer();
		sb.append("[QryBean]");
		sb.append("hql = \" "+hql+"\";");
		if(paramArray!=null){
			sb.append("paramArray = \"");
			for(Object o : paramArray){
				sb.append(o+";");
			}
			sb.append(" \"");
		}
		return sb.toString();
	}
}
