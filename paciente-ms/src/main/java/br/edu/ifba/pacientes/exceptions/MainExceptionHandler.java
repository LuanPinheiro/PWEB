package br.edu.ifba.pacientes.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(RegistroNotFoundException.class)
	public ResponseEntity<?> handleRegistroNotFoundException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "Paciente não encontrado");
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(InvalidFieldsException.class)
	public ResponseEntity<?> handleInvalidFieldsException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "Campos inválidos ou nulos");
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
