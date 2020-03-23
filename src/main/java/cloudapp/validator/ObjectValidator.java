package cloudapp.validator;

import org.springframework.stereotype.Component;

import cloudapp.web.BusinessException;

@Component
public class ObjectValidator {

	public void checkNotNull(Object value, String errorMessage) {
		if (value == null) {
			throw new BusinessException(errorMessage);
		}
	}
}