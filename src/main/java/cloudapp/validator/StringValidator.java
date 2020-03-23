package cloudapp.validator;

import org.springframework.stereotype.Component;

import cloudapp.web.BusinessException;

@Component
public class StringValidator {

	private static final String BLANK = "";

	public void checkNotNull(String value, String errorMessage) {
		if (value == null) {
			throw new BusinessException(errorMessage);
		}
	}

	public void checkNotBlank(String value, String errorMessage) {
		if (value == null || BLANK.equals(value.trim())) {
			throw new BusinessException(errorMessage);
		}
	}

	public void checkMaxLength(String value, int maxAllowedLength, String errorMessage) {
		if (value.trim().length() > maxAllowedLength) {
			throw new BusinessException(errorMessage);
		}
	}

	public void checkMinLength(String value, int minAllowedLength, String errorMessage) {
		if (value.trim().length() < minAllowedLength) {
			throw new BusinessException(errorMessage);
		}
	}
}