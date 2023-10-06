package br.edu.ifba.clinicamonolith.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.clinicamonolith.dtos.PacienteAtualizar;
import br.edu.ifba.clinicamonolith.dtos.PacienteEnviar;
import br.edu.ifba.clinicamonolith.dtos.PacienteListar;
import br.edu.ifba.clinicamonolith.exceptions.InvalidFieldsException;
import br.edu.ifba.clinicamonolith.exceptions.RegistroNotFoundException;
import br.edu.ifba.clinicamonolith.services.PacienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	@Autowired
	private PacienteService pacienteService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<PacienteListar> listarPacientes(@RequestParam("page") int page) {
		final Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "dadosPessoais.nome"));
		return pacienteService.listarTodos(pageable);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void novoPaciente(@Valid @RequestBody PacienteEnviar dadosPaciente){
		
		pacienteService.novoRegistro(dadosPaciente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizaPaciente(
			@NotNull @Valid @RequestBody PacienteAtualizar dadosPaciente,
			@PathVariable Long id) 
				throws RegistroNotFoundException, InvalidFieldsException {
		
		pacienteService.atualizaRegistro(dadosPaciente, id);
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeMedico(@PathVariable Long id) throws RegistroNotFoundException {
		
		pacienteService.removeRegistro(id);
		
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
	public Map<String, String> handleRegistroNotFoundException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "Paciente não encontrado");
	    return errors;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidFieldsException.class)
	public Map<String, String> handleInvalidFieldsException() {
	    
		Map<String, String> errors = new HashMap<String, String>();
        errors.put("message", "Campos inválidos ou nulos");
	    return errors;
	}
}
