package br.edu.ifba.trabalho.controller;

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

import br.edu.ifba.trabalho.dtos.MedicoAtualizar;
import br.edu.ifba.trabalho.dtos.MedicoEnviar;
import br.edu.ifba.trabalho.dtos.MedicoListar;
import br.edu.ifba.trabalho.exceptions.InvalidFieldsException;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;
import br.edu.ifba.trabalho.services.MedicoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<MedicoListar> listarMedicos(@RequestParam("page") int page) {
		final Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "dadosPessoais.nome"));
		return medicoService.listarTodos(pageable);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void novoMedico(@Valid @RequestBody MedicoEnviar dadosMedico){
		
		medicoService.novoRegistro(dadosMedico);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizaMedico(
			@NotNull @Valid @RequestBody MedicoAtualizar dadosMedico,
			@PathVariable Long id) 
				throws RegistroNotFoundException, InvalidFieldsException {
		
		medicoService.atualizaRegistro(dadosMedico, id);
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeMedico(@PathVariable Long id) 
			throws RegistroNotFoundException {
		
		medicoService.removeRegistro(id);
		
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
        errors.put("message", "Médico não encontrado");
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
