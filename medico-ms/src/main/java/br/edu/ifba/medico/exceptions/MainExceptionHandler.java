package br.edu.ifba.medico.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(RegistroNotFoundException.class)
	public ResponseEntity<?> handleRegistroNotFoundException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "Médico não encontrado");
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(InvalidFieldsException.class)
	public ResponseEntity<?> handleInvalidFieldsException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "Campos inválidos ou nulos");
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
	    
		Map<String, String> errors = new HashMap<String, String>();
		String errorMessage = new ArrayList<>(ex.getConstraintViolations()).get(0).getMessage();
        
		errors.put("message", errorMessage);
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatchException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "Especialidade Inválida");
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
		Map<String, String> errors = new HashMap<String, String>();
		if(ex.getMessage().contains("Especialidade")) {
			return handleMethodArgumentTypeMismatchException();			
		}
		else {
			errors.put("message", ex.getMessage());
		}
		return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(RegistroExistenteException.class)
	public ResponseEntity<?> handleRegistroExistenteException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "CRM já registrado com outro médico");
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request){
	    
		Map<String, String> errors = new HashMap<String, String>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
