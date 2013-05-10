package com.eds.ctcb.dao;
// default package

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.eds.ctcb.bean.PageBean;
import com.eds.ctcb.bean.QryBean;
import com.eds.ctcb.util.DataUtil;



public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao{


	
    public List qryInList(QryBean qryBean) {
    	return  this.getHibernateTemplate().find(qryBean.getHql(),qryBean.getParamArray()); 
  	}
	
    public List qryInList(final QryBean qryBean,final int firstResult,final int maxResults) {  
      return   getHibernateTemplate().executeFind(
    		new   HibernateCallback()   {  
    		  public   Object   doInHibernate(Session   s)   
    		  throws   HibernateException,   SQLException   {   
    		  Query   query   =   s.createQuery(qryBean.getHql());
    		  if(qryBean.getParamArray() != null){
    			  for(int i=0;i< qryBean.getParamArray().length;i++){
    				  query.setParameter(i, qryBean.getParamArray()[i]);
    			  }
    		  }
    		  query.setFirstResult(firstResult);
    		  query.setMaxResults(maxResults);   
    		  List   list   =   query.list();   
    		  return   list;   
    		  }
   		  });     	
  	}
    
    public PageBean qryInPage(QryBean qryBean,int pageSize,int pageNumber) { 
   		int count = this.getRecordCount(qryBean);
   		List currentList = this.qryInList(qryBean,(pageNumber-1)*pageSize,pageSize);
   		PageBean pageBean = new PageBean(currentList,count,pageSize,pageNumber);
   		return pageBean;
  	}
    
    public int getRecordCount(final QryBean qryBean) {    
    	int recordCount = 0;
    	List tempList = this.getHibernateTemplate().find("select count(*) "+qryBean.getHql(),qryBean.getParamArray()); 
    	if(tempList != null && tempList.size()==1 && tempList.get(0) instanceof Integer){
    		recordCount = ((Integer) tempList.get(0)).intValue();  
    	}
        return recordCount;
  	}
    
    public void create(Object o) {
        this.getHibernateTemplate().save(o);
    }
    
    public void update(Object o) {
        this.getHibernateTemplate().update(o);
    }

    public void merge(Object o) {
        this.getHibernateTemplate().merge(o);
    }
    
    public void delete(Object o) {
        this.getHibernateTemplate().delete(o);
    }
    
    public Object findById(Class entityClass ,Serializable id) {
        return this.getHibernateTemplate().get(entityClass , id);
    }
    
    public Date getSysdate(){
    	List<String> l = this.getHibernateTemplate().find("select to_char(sysdate,'yyyy/mm/dd HH24:MI:SS') from User");
    	String s = l.get(0);
    	
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        sdf.setLenient(false);
        try{
            d = sdf.parse(s);
        }catch(Exception e){
            
        } 

    	return d;
    	
    }
}