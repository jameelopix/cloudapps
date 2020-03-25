package cloudapp.entity;

public class StringColumn extends Column {

	private boolean isMultiLine;

	private String regexPattern;

	private Integer minLengthAllowed;

	private Integer maxLengthAllowed;

	public boolean isMultiLine() {
		return isMultiLine;
	}

	public void setMultiLine(boolean isMultiLine) {
		this.isMultiLine = isMultiLine;
	}

	public String getRegexPattern() {
		return regexPattern;
	}

	public void setRegexPattern(String regexPattern) {
		this.regexPattern = regexPattern;
	}

	public Integer getMinLengthAllowed() {
		return minLengthAllowed;
	}

	public void setMinLengthAllowed(Integer minLengthAllowed) {
		this.minLengthAllowed = minLengthAllowed;
	}

	public Integer getMaxLengthAllowed() {
		return maxLengthAllowed;
	}

	public void setMaxLengthAllowed(Integer maxLengthAllowed) {
		this.maxLengthAllowed = maxLengthAllowed;
	}

}