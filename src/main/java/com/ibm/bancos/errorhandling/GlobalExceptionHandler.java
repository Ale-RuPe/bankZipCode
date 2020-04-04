package com.ibm.bancos.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ibm.bancos.model.ErrorResponse;
import com.ibm.bancos.model.exceptions.BadRequestException;
import com.ibm.bancos.model.exceptions.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler {
	
	/**
	 * Handle BadRequestException
	 * @param e: the exception to handle
	 * @return custom ResponseEntity
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException( BadRequestException e){
		log.info("Missing Params Exception");
		ErrorResponse response = getErrorResponse( 
				ErrorType.ERROR.toString(), 
				String.valueOf( HttpStatus.BAD_REQUEST.value()), 
				e.getMessage(), e.getLocation(), e.getUri() );
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handle NotFoundException
	 * @param e: the exception to handle
	 * @return custom ResponseEntity
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException( NotFoundException e){
		log.info("Missing Params Exception");
		ErrorResponse response = getErrorResponse( 
				ErrorType.ERROR.toString(), 
				String.valueOf( HttpStatus.NOT_FOUND.value()), 
				e.getMessage(), e.getLocation(), e.getUri() );
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handle MissingServletRequestParameterException
	 * @param ex: the exception
	 * @return custom ResponseEntity
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException ex) {
		log.info("Missing Params Exception");
		
		ErrorResponse response = getErrorResponse( 
				ErrorType.ERROR.toString(), 
				String.valueOf( HttpStatus.BAD_REQUEST.value()), 
				"${exceptions.message.badrequest}", ex.getMessage(), ex.getLocalizedMessage() );
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handle NoHandlerFoundException
	 * @param ex: the exception
	 * @return
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		log.info("No Handler Found Exception");
		
		ErrorResponse response = getErrorResponse( 
					ErrorType.ERROR.toString(), 
					String.valueOf( HttpStatus.NOT_FOUND.value()), 
					"${exceptions.message.notfound}", ex.getRequestURL(), ex.getHttpMethod() );
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Build an ErrorResponse Object
	 * @param type
	 * @param code
	 * @param details
	 * @param location
	 * @param more
	 * @return
	 */
	private ErrorResponse getErrorResponse(String type, String code,String details, String location, String more) {
		ErrorResponse response = new ErrorResponse();
	    response.setType(type);
	    response.setCode(code);
	    response.setDetails(details);
	    response.setLocation(location);
	    response.setMoreInfo(more);
	    
	    log.info("Response {},{},{},{},{}", response.getType(), response.getCode(), response.getDetails(),
		        response.getLocation(), response.getMoreInfo() );
	    return response;
	}
}