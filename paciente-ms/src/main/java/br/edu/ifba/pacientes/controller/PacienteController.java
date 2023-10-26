package br.edu.ifba.pacientes.controller;

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

import br.edu.ifba.pacientes.dtos.PacientaConsulta;
import br.edu.ifba.pacientes.dtos.PacienteAtualizar;
import br.edu.ifba.pacientes.dtos.PacienteEnviar;
import br.edu.ifba.pacientes.dtos.PacienteListar;
import br.edu.ifba.pacientes.exceptions.InvalidFieldsException;
import br.edu.ifba.pacientes.exceptions.RegistroNotFoundException;
import br.edu.ifba.pacientes.models.Paciente;
import br.edu.ifba.pacientes.services.PacienteService;
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
	public ResponseEntity<?> removePaciente(@PathVariable Long id) throws RegistroNotFoundException {
		
		pacienteService.removeRegistro(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/encontrarPorId/{id}")
	public ResponseEntity<PacientaConsulta> encontrarPorId(@PathVariable Long id) 
			throws RegistroNotFoundException {
		
		Paciente paciente = pacienteService.encontrarPorId(id);
		
		return new ResponseEntity<>(new PacientaConsulta(paciente),HttpStatus.OK);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    
		Map<String, String> errors = new HashMap<String, String>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
