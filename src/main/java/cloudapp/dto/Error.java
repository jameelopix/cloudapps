package cloudapp.dto;

public class Error {
	private String code;

	private String message;

	public Error(String errorMessage) {
		this.setMessage(errorMessage);
	}

	public Error(String errorCode, String errorMessage) {
		this.setCode(errorCode);
		this.setMessage(errorMessage);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}