package cloudapp.component;

public class StringColumn extends Column {

	private boolean isMultiLine;

	private int minLengthAllowed = 0;

	private int maxLengthAllowed = 4098;

	public boolean isMultiLine() {
		return isMultiLine;
	}

	public void setMultiLine(boolean isMultiLine) {
		this.isMultiLine = isMultiLine;
	}

	public int getMinLengthAllowed() {
		return minLengthAllowed;
	}

	public void setMinLengthAllowed(int minLengthAllowed) {
		this.minLengthAllowed = minLengthAllowed;
	}

	public int getMaxLengthAllowed() {
		return maxLengthAllowed;
	}

	public void setMaxLengthAllowed(int maxLengthAllowed) {
		this.maxLengthAllowed = maxLengthAllowed;
	}
}