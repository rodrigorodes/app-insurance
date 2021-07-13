package br.com.grupososseg.commons.error;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationErrorHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler({ ConstraintViolationException.class })
	public ModelAndView handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
		logger.error("400 Status Code", ex);
		ModelAndView model = new ModelAndView("error/403");
		model.addObject("message", ex.getMessage());
		return model;
	}

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		return new ModelAndView("error/500");
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final String bodyOfResponse = "RestResponseExceptionHandler - 3";
		return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ErrorResponse> errorsReponse = new CreateErrorResponseAPI(fieldErrors, messageSource).create();

		return handleExceptionInternal(ex, errorsReponse, headers, HttpStatus.BAD_REQUEST, request);
	}

	private static class CreateErrorResponseAPI {

		private List<ErrorResponse> errors = new ArrayList<>();
		private List<FieldError> fieldErrors;
		private MessageSource messageSource;

		public CreateErrorResponseAPI(List<FieldError> fieldErrors, MessageSource messageSource) {
			this.fieldErrors = fieldErrors;
			this.messageSource = messageSource;
		}

		public List<ErrorResponse> create() {
			fieldErrors.forEach(fieldError -> {
				String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
				errors.add(new ErrorResponse(message, fieldError.getField(), fieldError.getRejectedValue()));

			});
			return errors;
		}
	}
}
