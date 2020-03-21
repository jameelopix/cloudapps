package cloudapp.validator;

import org.springframework.stereotype.Component;

import cloudapp.common.CONFIG;
import cloudapp.dto.AppRequest;
import cloudapp.dto.ErrorResponse;

@Component
public class AppValidator {

	public ErrorResponse checkAppNameLength(AppRequest appRequest) {
		System.out.println("AppValidator.checkAppNameLength()");
		String baseName = appRequest.getAppDTO().getDisplayName();
		if (baseName.trim().length() > CONFIG.APPNAME_PERMISSIONABLE_LENGTH) {
			return new ErrorResponse("Err_101", "App Name can't be more than " + CONFIG.APPNAME_PERMISSIONABLE_LENGTH);
		}
		return null;
	}
}