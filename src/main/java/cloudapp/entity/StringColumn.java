package cloudapp.entity;

public class StringColumn extends Column {

	private boolean isMultiLine;

	private String regexPattern;

	private int minLengthAllowed;

	private int maxLengthAllowed;

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

	public String getRegexPattern() {
		return regexPattern;
	}

	public void setRegexPattern(String regexPattern) {
		this.regexPattern = regexPattern;
	}
}