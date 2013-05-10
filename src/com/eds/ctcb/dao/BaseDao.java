package com.eds.ctcb.dao;
// default package

import java.io.Serializable;
import java.util.List;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import java.util.Date;
public interface BaseDao {
	
    public List qryInList(QryBean qryBean);
	
    //public List qryInList(final QryBean qryBean,final int firstResult,final int maxResults) ;
    
    public PageBean qryInPage(QryBean qryBean,int pageSize,int pageNumber);
    
    //public int getRecordCount(final QryBean qryBean) ;
    
    public void create(Object o) ;
    
    public void update(Object o) ;

    public void merge(Object o) ;
    
    public void delete(Object o) ;
    
    public Object findById(Class entityClass ,Serializable id) ;
    
	public Date getSysdate();
}