package com.eds.ctcb.bean;

import java.io.Serializable;
import java.util.List;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
/**
 * The  class implements the interface "org.displaytag.pagination.PaginatedList", which 
 * describing an externally sorted and paginated list.
 * 
 * @remark
 *   used in the display:table tag
 * 
 * @author ezenwan
 *
 */
public class PageBean implements PaginatedList,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List list;
	private int pageNumber = 1;
    private int objectsPerPage = 20;
    private int fullListSize = 0;
    private String sortCriterion;
    private SortOrderEnum sortDirection;
    private String searchId;
    
    //
    /**
	 * Returns the size of the full list
	 */
	public int getFullListSize() {
		return this.fullListSize;
	}
	/**
	 * Set the size of the full list 
	 * @param fullListSize
	 */
	public void setFulListSize(int fullListSize){
		this.fullListSize = fullListSize;
	}

	/**
	 * Returns the current partial list
	 */
	public List getList() {
		return this.list;
	}
	/**
	 * Set the current partial list
	 * @param list
	 */
	public void setList(List list){
		this.list = list;
	}

	/**
	 * Returns the number of objects per page. 
	 * Unless this page is the last one the partial list should thus have a size equal to the result of this method
	 */
	public int getObjectsPerPage() {
		return this.objectsPerPage;
	}
	/**
	 * Set the number of th objects per page.
	 * @param objectPerPage
	 */
	public void setObjectPerPage(int objectPerPage){
		this.objectsPerPage = objectPerPage;
	}

	/**
	 * Returns the page number of the partial list (starts from 1)
	 */
	public int getPageNumber() {
		return this.pageNumber;
	}
	/**
	 * Set the page number.
	 * @param pageNumber
	 */
	public void setPageNumber(int pageNumber){
		this.pageNumber = pageNumber;
	}
	

	/**
	 * Returns an ID for the search used to get the list. It may be null.
	 * Such an ID can be necessary if the full list is cached, in a way or another (in the session, in the business 
	 * tier, or anywhere else), to be able to retrieve the full list from the cache
	 */
	public String getSearchId() {
		return this.searchId;
	}
	/**
	 * Set the ID for the search used to get the list.
	 * @param searchId
	 */
	public void setSearchId(String searchId){
		this.searchId = searchId;
	}

	/**
	 * Returns the sort direction used to externally sort the full list
	 */
	public String getSortCriterion() {
		return this.sortCriterion;
	}
	/**
	 * Set the sortCriterion
	 * @param sortCriterion
	 */
	public void setSortCriterion(String sortCriterion){
		this.sortCriterion = sortCriterion;
	}

	/**
	 * Returns the sort direction used to externally sort the full list
	 */
	public SortOrderEnum getSortDirection() {
		return this.sortDirection;
	}
	/**
	 * Set the sort Direction
	 * @param sortDirection
	 */
	public void setSortDirection(SortOrderEnum sortDirection){
		this.sortDirection = sortDirection;
	}
	
	public PageBean(List currentList,int FullListSize,int objectPerPage,int pageNumber){
		this.setList(currentList);
		this.setFulListSize(FullListSize);
		this.setObjectPerPage(objectPerPage);
		this.setPageNumber(pageNumber);	
		/*
		if(this.getPageNumber()-1 > this.getFullListSize()/this.getObjectsPerPage()){
			this.setPageNumber(this.getFullListSize()/this.getObjectsPerPage());
		}
		*/
	}

	/*
	public static void saveInSession(HttpSession session,String sessionKey,PaginateListImpl paginateList){
		session.setAttribute(sessionKey, paginateList);
	}
	
	public static PaginateListImpl fetchFromSession(HttpSession session,String sessionKey){
		PaginateListImpl paginateList = null;
		if(session != null && session.getAttribute(sessionKey)!=null && session.getAttribute(sessionKey) instanceof PaginateListImpl){
			paginateList = (PaginateListImpl)session.getAttribute(sessionKey);
		}
		return paginateList;
	}
	*/
}
