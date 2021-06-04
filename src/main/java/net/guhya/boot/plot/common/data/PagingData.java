package net.guhya.boot.plot.common.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PagingData {
	
	private String condition;
	private String keyword;
	private String sortColumn;
	private String sortOrder;
	private int page;
	private int pageSize;
	private int startRow;
	private long totalRecords;
	private long lastRow;
	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public long getLastRow() {
		return this.lastRow;
	}
	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PagingData [condition=");
		builder.append(condition);
		builder.append(", keyword=");
		builder.append(keyword);
		builder.append(", sortColumn=");
		builder.append(sortColumn);
		builder.append(", sortOrder=");
		builder.append(sortOrder);
		builder.append(", page=");
		builder.append(page);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", startRow=");
		builder.append(startRow);
		builder.append(", totalRecords=");
		builder.append(totalRecords);
		builder.append(", lastRow=");
		builder.append(lastRow);
		builder.append("]");
		return builder.toString();
	}

}
