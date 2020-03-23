package cloudapp.dto;

import java.util.Map;

public class DatabaseRequest extends ParentRequest {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> values;

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}

}