package cloudapp.jpa;

import java.util.List;

public class Filter {

	private String fieldName;

	private Operator operator;

	private Object fieldValue;

	private Object fromValue;

	private Object toValue;

	private List<Filter> filters;

	public Filter(String fieldName) {
		this.fieldName = fieldName;
	}

	public Filter by(Operator operator) {
		this.setOperator(operator);
		return this;
	}

	public Filter with(Object fieldValue) {
		this.setFieldValue(fieldValue);
		return this;
	}

	public Filter from(Object fromValue) {
		this.setFromValue(fromValue);
		return this;
	}

	public Filter to(Object toValue) {
		this.setToValue(toValue);
		return this;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Operator getOperator() {
		return operator;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public Object getFromValue() {
		return fromValue;
	}

	public Object getToValue() {
		return toValue;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	public void setFromValue(Object fromValue) {
		this.fromValue = fromValue;
	}

	public void setToValue(Object toValue) {
		this.toValue = toValue;
	}
}