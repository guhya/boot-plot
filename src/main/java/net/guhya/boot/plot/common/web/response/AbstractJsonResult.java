package net.guhya.boot.plot.common.web.response;

public class AbstractJsonResult {
	
	protected static String SUCCESS = "success";
	protected static String ERROR = "error";
	
	private String status;
	private String error;
	private String message;
	private String timestamp;
	
	public AbstractJsonResult() {
	}

	public AbstractJsonResult(String status, String timestamp) {
		this.status = status;
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractJsonResult [status=");
		builder.append(status);
		builder.append(", error=");
		builder.append(error);
		builder.append(", message=");
		builder.append(message);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append("]");
		return builder.toString();
	}
	
	

}
