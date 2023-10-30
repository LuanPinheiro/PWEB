package br.edu.ifba.consulta.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(RegistroNotFoundException.class)
	public ResponseEntity<?> handleRegistroNotFoundException(RegistroNotFoundException ex) {
	    
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("message", ex.getMessage());
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(ConsultaExistenteException.class)
	public ResponseEntity<?> handleConsultaExistenteException() {
		
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("message", "Essa consulta já foi agendada");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(DataInvalidaException.class)
	public ResponseEntity<?> handleDataInvalidaException(DataInvalidaException ex) {
		
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(ConsultaNotFoundException.class)
	public ResponseEntity<?> handleConsultaNotFoundException(ConsultaNotFoundException ex) {
		
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(CantCancelConsultaException.class)
	public ResponseEntity<?> handleCantCancelConsultaException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("message", "A consulta só pode ser cancelada com 24h de antecedência");
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(PacienteJaAgendadoException.class)
	public ResponseEntity<?> handlePacienteJaAgendadoException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("message", "Esse paciente já possui consulta agendada para esse dia");
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(MedicoUnavailableException.class)
	public ResponseEntity<?> handleMedicoUnavailableException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
		errors.put("message", "O médico indicado não está disponível nesse horário");
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
