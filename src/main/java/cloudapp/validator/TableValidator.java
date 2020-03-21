package cloudapp.validator;

import org.springframework.stereotype.Component;

import cloudapp.common.CONFIG;
import cloudapp.dto.ErrorResponse;
import cloudapp.dto.TableRequest;

@Component
public class TableValidator {

	public ErrorResponse checkTableNameLength(TableRequest tableRequest) {
		String baseName = tableRequest.getTable().getName();
		if (baseName.trim().length() > CONFIG.APPNAME_PERMISSIONABLE_LENGTH) {
			return new ErrorResponse("Err_101",
					"Table Name can't be more than " + CONFIG.APPNAME_PERMISSIONABLE_LENGTH);
		}
		return null;
	}
}