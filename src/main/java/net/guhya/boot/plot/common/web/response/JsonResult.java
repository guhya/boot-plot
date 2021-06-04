package net.guhya.boot.plot.common.web.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"timestamp", "status", "meta", "data", "error", "message"})
public class JsonResult<T> extends AbstractJsonResult {
	
	private T data;
	
	public JsonResult() {
	}

	/**
	 * Response for successful operation
	 * @param result
	 */
	public JsonResult(T result) {
		super(AbstractJsonResult.SUCCESS, LocalDateTime.now().toString());
		this.data = result;
	}

	/**
	 * Response for error
	 * @param errorCode
	 * @param ex
	 */
	public JsonResult(String errorCode, String message) {
		super(AbstractJsonResult.ERROR, LocalDateTime.now().toString());
		setMessage(message);
	}

	/**
	 * Response for error
	 * @param errorCode
	 * @param ex
	 */
	public JsonResult(String errorCode, T errors) {
		super(AbstractJsonResult.ERROR, LocalDateTime.now().toString());
		setError(errorCode);
		this.data = errors;
	}
	

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	
}
