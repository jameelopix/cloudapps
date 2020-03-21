package cloudapp.validator;

import org.springframework.stereotype.Component;

import cloudapp.dto.ErrorResponse;

@Component
public class StringValidator {

    public ErrorResponse checkMaxLength(String value, int maxAllowedLength, String errorMessage) {
	if (value.trim().length() > maxAllowedLength) {
	    return new ErrorResponse(errorMessage);
	}
	return null;
    }

    public ErrorResponse checkMinLength(String value, int minAllowedLength, String errorMessage) {
	if (value.trim().length() < minAllowedLength) {
	    return new ErrorResponse(errorMessage);
	}
	return null;
    }
}