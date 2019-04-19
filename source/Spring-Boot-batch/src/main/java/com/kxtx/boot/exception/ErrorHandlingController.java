/**
 * 
 */
package com.kxtx.boot.exception;

import java.util.List;
import java.util.Locale;

import com.kxtx.boot.utility.StaticVariables;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorHandlingController {

	@Autowired
	private MessageSource messageSource;

	/**
	 * Provides handling for standard Spring MVC exceptions.
	 * @param ex the target exception
	 * @param request the current request
	 */
	@ExceptionHandler({
		HttpRequestMethodNotSupportedException.class,
		HttpMediaTypeNotSupportedException.class,
		HttpMediaTypeNotAcceptableException.class,
		MissingPathVariableException.class,
		MissingServletRequestParameterException.class,
		ServletRequestBindingException.class,
		ConversionNotSupportedException.class,
		TypeMismatchException.class,
		HttpMessageNotReadableException.class,
		HttpMessageNotWritableException.class,
		MissingServletRequestPartException.class,
		BindException.class,
		NoHandlerFoundException.class,
		AsyncRequestTimeoutException.class
	})
	public final ResponseEntity<ExceptionResponse> handleException(Exception ex) {
		ExceptionResponse er=new ExceptionResponse();
		Locale currentLocale =  LocaleContextHolder.getLocale();
		if (ex instanceof HttpRequestMethodNotSupportedException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1100, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.METHOD_NOT_ALLOWED);
		}else if (ex instanceof HttpMediaTypeNotSupportedException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1101, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		}else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1102, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.NOT_ACCEPTABLE);
		}else if (ex instanceof MissingPathVariableException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1103, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.INTERNAL_SERVER_ERROR);
		}else if (ex instanceof MissingServletRequestParameterException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1104, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.BAD_REQUEST);
		}else if (ex instanceof ServletRequestBindingException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1104, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.BAD_REQUEST);
		}else if (ex instanceof ConversionNotSupportedException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1103, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.INTERNAL_SERVER_ERROR);
		}else if (ex instanceof TypeMismatchException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1104, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.BAD_REQUEST);
		}else if (ex instanceof HttpMessageNotReadableException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1104, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.BAD_REQUEST);
		}else if (ex instanceof HttpMessageNotWritableException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1103, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.INTERNAL_SERVER_ERROR);
		}else if (ex instanceof MissingServletRequestPartException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1104, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.BAD_REQUEST);
		}else if (ex instanceof BindException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1104, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.BAD_REQUEST);
		}else if (ex instanceof NoHandlerFoundException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1106, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.NOT_FOUND);
		}else if (ex instanceof AsyncRequestTimeoutException) {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1105, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er,HttpStatus.SERVICE_UNAVAILABLE);
		}else {
			er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_1103, null, currentLocale));
			return new ResponseEntity<ExceptionResponse>(er, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionResponse>customeException(){
		ExceptionResponse er=new ExceptionResponse();
		Locale currentLocale =  LocaleContextHolder.getLocale();
		er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_2100, null, currentLocale));
		return new ResponseEntity<ExceptionResponse>(er,HttpStatus.NOT_FOUND);
	}

//	@ExceptionHandler(BatchExceptions.class)
//    public ResponseEntity<ExceptionResponse>batchException(){
//	    ExceptionResponse er=new ExceptionResponse();
//        Locale currentLocale =  LocaleContextHolder.getLocale();
//        er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_5100, null, currentLocale));
//        return new ResponseEntity<ExceptionResponse>(er,HttpStatus.SERVICE_UNAVAILABLE);
//    }
//
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ExceptionResponse>allException(){
//		ExceptionResponse er=new ExceptionResponse();
//		Locale currentLocale =  LocaleContextHolder.getLocale();
//		er.setMessage(messageSource.getMessage(StaticVariables.CRUD_WEB_ERRORS_5100, null, currentLocale));
//		return new ResponseEntity<ExceptionResponse>(er,HttpStatus.SERVICE_UNAVAILABLE);
//	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorDTO> processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		return new ResponseEntity<ValidationErrorDTO>(processFieldErrors(fieldErrors),HttpStatus.BAD_REQUEST);
	}

	private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
		ValidationErrorDTO dto = new ValidationErrorDTO();
		Locale currentLocale =  LocaleContextHolder.getLocale();
		for (FieldError fieldError: fieldErrors) {
			String localizedErrorMessage = messageSource.getMessage(fieldError.getDefaultMessage(),null, currentLocale);
			dto.addFieldError(fieldError.getField(), localizedErrorMessage);
		}

		return dto;
	}
}
