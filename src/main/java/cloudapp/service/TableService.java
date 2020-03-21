package cloudapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cloudapp.component.TableComponent;
import cloudapp.dto.ErrorResponse;
import cloudapp.dto.TableRequest;
import cloudapp.dto.TableResponse;
import cloudapp.validator.TableValidator;

@Service
public class TableService {

	@Autowired
	TableComponent tableComponent;

	@Autowired
	TableValidator tableValidator;

	public TableResponse createTable(TableRequest tableRequest) {
		tableComponent.preprocessTableRequest(tableRequest);

		ErrorResponse errorResponse = tableValidator.checkTableNameLength(tableRequest);

		if (errorResponse == null) {
			return tableComponent.createTable(tableRequest);
		} else {
			TableResponse tableResponse = new TableResponse();
			tableResponse.setErrorCode(errorResponse.getErrorCode());
			tableResponse.setErrorMessage(errorResponse.getErrorMessage());
			return tableResponse;
		}
	}

	public TableResponse deleteTable(TableRequest tableRequest) {
		return tableComponent.deleteTable(tableRequest);
	}

	public TableResponse getTable(TableRequest tableRequest) {
		return tableComponent.getTable(tableRequest);
	}

	public TableResponse updateTable(TableRequest tableRequest) {
		tableComponent.preprocessTableRequest(tableRequest);

		ErrorResponse errorResponse = tableValidator.checkTableNameLength(tableRequest);

		if (errorResponse == null) {
			return tableComponent.updateTable(tableRequest);
		} else {
			TableResponse tableResponse = new TableResponse();
			tableResponse.setErrorCode(errorResponse.getErrorCode());
			tableResponse.setErrorMessage(errorResponse.getErrorMessage());
			return tableResponse;
		}
	}
}