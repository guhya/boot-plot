
package net.guhya.boot.plot.common.web;

import org.springframework.context.annotation.PropertySource;

import net.guhya.boot.plot.common.data.PagingData;
import net.guhya.boot.plot.common.web.request.Box;

@PropertySource({ 
  "classpath:/application${env:}.properties"
})
public abstract class AbstractRestController {
	
	public static final int PAGE_SIZE_VAL			= 10;
	
	public static final String PARAM_PAGE			= "page";
	public static final String PARAM_START_ROW		= "startRow";
	public static final String PARAM_PAGE_SIZE		= "pageSize";
	public static final String PARAM_CONDITION		= "condition";
	public static final String PARAM_KEYWORD		= "keyword";
	public static final String PARAM_SORT_COLUMN	= "sortColumn";
	public static final String PARAM_SORT_ORDER		= "sortOrder";
	public static final String PARAM_QUERY_STRING	= "queryString";
	
	public static final String CURRENT				= "current";
	public static final String PAGE_SIZE			= "pageSize";
	public static final String LAST_ROW				= "lastRow";
	public static final String TOTAL_RECORDS		= "totalRecords";
	
	public static final String FILE_LIST			= "fileList";
	public static final String REQ_DELETED_FILES	= "deletedFiles";
	
	public static final String SUCCESS				= "success";
	public static final String FAIL					= "fail";

	/**
	 * Prepare parameter to be used in paging, adjust the start row
	 * @param paramBox
	 * @param totalRecords
	 */
	protected void listPaging(Box paramBox, int totalRecords) {
		
		paramBox.put(TOTAL_RECORDS, totalRecords);
		int pageSize = paramBox.getInt(PARAM_PAGE_SIZE);
		int currentPage = paramBox.getInt(PARAM_PAGE);
		
		//Convert page parameter type from string to integer		
		if(pageSize > 0){
			paramBox.put(PARAM_PAGE_SIZE, pageSize);
		}
		
		//Calculate starting row from current page and page size value
		currentPage = currentPage < 1 ? 1 : currentPage;
		if(currentPage > 0){
			int newCurrent = getCurrentPage(totalRecords, pageSize, currentPage); 
			int startRow = (newCurrent-1) * pageSize;
			paramBox.put(PARAM_START_ROW, startRow);
			paramBox.put(LAST_ROW, startRow+pageSize);
		}
		
	}
	
	protected PagingData getPagingData(Box paramBox) {
		PagingData meta = new PagingData();
		meta.setTotalRecords(paramBox.getInt(TOTAL_RECORDS));
		meta.setPageSize(paramBox.getInt(PARAM_PAGE_SIZE));
		meta.setPage(paramBox.getInt(PARAM_PAGE));
		meta.setStartRow(paramBox.getInt(PARAM_START_ROW));
		meta.setCondition(paramBox.getString(PARAM_CONDITION));
		meta.setKeyword(paramBox.getString(PARAM_KEYWORD));
		meta.setSortColumn(paramBox.getString(PARAM_SORT_COLUMN));
		meta.setSortOrder(paramBox.getString(PARAM_SORT_ORDER));
		
		return meta;
	}
	
	/**
	 * Get current page after adjustment with the page size
	 * @param totalRecords
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	protected int getCurrentPage(int totalRecords, int pageSize, int currentPage) {
		double p = Math.ceil(totalRecords / (double) pageSize);
		int newCurrent = p < currentPage ? 1 : currentPage; 
		
		return newCurrent;
	}
	
}
