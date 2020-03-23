package cloudapp.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import cloudapp.dto.Error;
import cloudapp.dto.ParentResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public ParentResponse handleCityNotFoundException(BusinessException ex, WebRequest request) {
		ParentResponse response = new ParentResponse();
		Error error = new Error(ex.getCode(), ex.getMessage());
		response.setError(error);
		return response;
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DatabaseException.class)
	@ResponseBody
	public ParentResponse handleCityNotFoundException(DatabaseException ex, WebRequest request) {
		ParentResponse response = new ParentResponse();
		Error error = new Error(ex.getCode(), ex.getMessage());
		response.setError(error);
		return response;
	}
}