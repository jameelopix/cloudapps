package cloudapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloudapp.component.AppComponent;
import cloudapp.dto.AppRequest;
import cloudapp.dto.AppResponse;
import cloudapp.dto.ErrorResponse;
import cloudapp.validator.AppValidator;

@Service
public class AppService {

	@Autowired
	AppComponent appComponent;

	@Autowired
	AppValidator appValidator;

	public AppResponse createApp(AppRequest appRequest) {
		appComponent.preprocessAppRequest(appRequest);

		ErrorResponse errorResponse = appValidator.checkAppNameLength(appRequest);

		if (errorResponse == null) {
			return appComponent.createApp(appRequest);
		} else {
			AppResponse appResponse = new AppResponse();
			appResponse.setErrorCode(errorResponse.getErrorCode());
			appResponse.setErrorMessage(errorResponse.getErrorMessage());
			return appResponse;
		}
	}

	public AppResponse deleteApp(AppRequest appRequest) {
		return appComponent.deleteApp(appRequest);
	}

	// public AppResponse updateDesc(AppRequest appRequest) {
	// return appComponent.updateDesc(appRequest);
	// }
	//
	// public AppResponse updateIcon(AppRequest appRequest) {
	// return appComponent.updateIcon(appRequest);
	// }
	//
	// public AppResponse updateName(AppRequest appRequest) {
	// return appComponent.updateName(appRequest);
	// }

	public AppResponse getApp(AppRequest appRequest) {
		return appComponent.getApp(appRequest);
	}

	// public AppResponse updateApp(AppRequest appRequest) {
	// ErrorResponse errorResponse = appValidator.checkAppNameLength(appRequest);
	//
	// if (errorResponse == null) {
	// return appComponent.updateApp(appRequest);
	// } else {
	// AppResponse appResponse = new AppResponse();
	// appResponse.setErrorCode(errorResponse.getErrorCode());
	// appResponse.setErrorMessage(errorResponse.getErrorMessage());
	// return appResponse;
	// }
	// }
}