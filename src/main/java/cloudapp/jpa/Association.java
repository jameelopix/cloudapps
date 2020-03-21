package cloudapp.jpa;

public class Association {
	private Class parent;

	private String attribute;

	private Boolean childless = false;

	public Class getParent() {
		return parent;
	}

	public void setParent(Class parent) {
		this.parent = parent;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Boolean getChildless() {
		return childless;
	}

	public void setChildless(Boolean childless) {
		this.childless = childless;
	}
}