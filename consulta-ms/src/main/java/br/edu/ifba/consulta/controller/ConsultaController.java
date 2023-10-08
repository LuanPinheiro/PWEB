package br.edu.ifba.consulta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.consulta.dtos.ConsultaCancelar;
import br.edu.ifba.consulta.dtos.ConsultaEnviar;
import br.edu.ifba.consulta.dtos.ConsultaListar;
import br.edu.ifba.consulta.exceptions.CantCancelConsultaException;
import br.edu.ifba.consulta.exceptions.ConsultaExistenteException;
import br.edu.ifba.consulta.exceptions.ConsultaNotFoundException;
import br.edu.ifba.consulta.exceptions.DataInvalidaException;
import br.edu.ifba.consulta.exceptions.MedicoUnavailableException;
import br.edu.ifba.consulta.exceptions.PacienteJaAgendadoException;
import br.edu.ifba.consulta.exceptions.RegistroNotFoundException;
import br.edu.ifba.consulta.services.ConsultaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
	
	@Autowired
	private ConsultaService consultaService;
	
	@GetMapping
	public List<ConsultaListar> listarConsultas(){
		return consultaService.listarConsultas();
	}
	
	@PostMapping
	public ResponseEntity<?> marcarConsulta(@Valid @RequestBody ConsultaEnviar dados) 
			throws RegistroNotFoundException,
			DataInvalidaException,
			ConsultaExistenteException,
			PacienteJaAgendadoException,
			MedicoUnavailableException{
		
		consultaService.marcarConsulta(dados);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping
	public ResponseEntity<?> cancelarConsulta(@Valid @RequestBody ConsultaCancelar dados) 
			throws RegistroNotFoundException,
			ConsultaNotFoundException,
			CantCancelConsultaException{
		
		consultaService.cancelarConsulta(dados);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
		
		Map<String, String> errors = new HashMap<String, String>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RegistroNotFoundException.class)
	public Map<String, String> handleRegistroNotFoundException(RegistroNotFoundException ex) {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", ex.getMessage());
	    return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataInvalidaException.class)
	public Map<String, String> handleDataInvalidaException(DataInvalidaException ex) {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", ex.getMessage());
	    return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConsultaExistenteException.class)
	public Map<String, String> handleConsultaExistenteException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "Essa consulta já foi agendada");
	    return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConsultaNotFoundException.class)
	public Map<String, String> handleConsultaNotFoundException(ConsultaNotFoundException ex) {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", ex.getMessage());
	    return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PacienteJaAgendadoException.class)
	public Map<String, String> handlePacienteJaAgendadoException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "Esse paciente já possui consulta agendada para esse dia");
	    return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MedicoUnavailableException.class)
	public Map<String, String> handleMedicoUnavailableException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "O médico indicado não está disponível nesse horário");
	    return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CantCancelConsultaException.class)
	public Map<String, String> handleCantCancelConsultaException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "A consulta só pode ser cancelada com 24h de antecedência");
	    return errors;
	}
	
	
}
