package cloudapp.component;

public class NumberColumn extends Column {

	private boolean isNegativeAllowed;

	private int minAllowed;

	private int maxAllowed;

	public boolean isNegativeAllowed() {
		return isNegativeAllowed;
	}

	public void setNegativeAllowed(boolean isNegativeAllowed) {
		this.isNegativeAllowed = isNegativeAllowed;
	}

	public int getMinAllowed() {
		return minAllowed;
	}

	public void setMinAllowed(int minAllowed) {
		this.minAllowed = minAllowed;
	}

	public int getMaxAllowed() {
		return maxAllowed;
	}

	public void setMaxAllowed(int maxAllowed) {
		this.maxAllowed = maxAllowed;
	}
}