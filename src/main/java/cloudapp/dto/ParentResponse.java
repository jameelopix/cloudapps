package cloudapp.dto;

import java.io.Serializable;
import java.util.List;

public class ParentResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Error error;

	private List<Error> errors;

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
}