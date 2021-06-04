package net.guhya.boot.plot.common.web.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import net.guhya.boot.plot.common.data.PagingData;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"timestamp", "status", "meta", "data", "error", "message"})
public class JsonListResult<T> extends AbstractJsonResult {
	
	private PagingData meta;
	private List<T> data;
	
	public JsonListResult() {
		super();
	}
	
	/**
	 * Response for list operation
	 * @param totalRecords
	 * @param currentPage
	 * @param result
	 */
	public JsonListResult(PagingData pagingData,
			List<T> result) {
		super(AbstractJsonResult.SUCCESS, LocalDateTime.now().toString());
		this.meta = pagingData;
		this.data = result;
	}

	public PagingData getMeta() {
		return meta;
	}

	public void setMeta(PagingData meta) {
		this.meta = meta;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}


}
