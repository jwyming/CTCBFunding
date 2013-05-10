package com.eds.ctcb.util;

import java.util.ArrayList;
import java.util.List;

import com.eds.ctcb.bean.PageBean;

public class PageUtil {
	public static PageBean doPagenate(List fullList,int objectPerPage,int pageNumber){
		List<Object> currentList = new ArrayList<Object>();
		int fromIndex = (pageNumber-1)*objectPerPage;
		int toIndex = fromIndex+objectPerPage-1;
		for(int i=fromIndex;i<=toIndex;i++){
			if(fullList.size()-1>=i){
				currentList.add(fullList.get(i));
			}
		}
		PageBean pageBean =new PageBean(currentList,fullList.size(),objectPerPage,pageNumber);
		
		return pageBean;
		
	}
	
}
