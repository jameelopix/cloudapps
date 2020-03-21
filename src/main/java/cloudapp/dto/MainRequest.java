package cloudapp.dto;

import java.io.Serializable;
import java.util.Map;

public class MainRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> stringifiedValues;

	public Map<String, Object> getStringifiedValues() {
		return stringifiedValues;
	}

	public void setStringifiedValues(Map<String, Object> stringifiedValues) {
		this.stringifiedValues = stringifiedValues;
	}
}