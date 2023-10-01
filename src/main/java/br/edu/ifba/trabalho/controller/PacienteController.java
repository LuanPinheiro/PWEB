package br.edu.ifba.trabalho.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.trabalho.dtos.PacienteEnviar;
import br.edu.ifba.trabalho.dtos.PacienteListar;
import br.edu.ifba.trabalho.exceptions.InvalidFieldsException;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;
import br.edu.ifba.trabalho.services.PacienteService;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	@Autowired
	private PacienteService pacienteService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<PacienteListar> listarPacientes(@RequestParam(required = false) Integer page) {
		return pacienteService.listarTodos(page);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void novoPaciente(@RequestBody PacienteEnviar dadosPaciente){
		pacienteService.novoRegistro(dadosPaciente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizaPaciente(@RequestBody PacienteEnviar dadosPaciente, @PathVariable Long id) {
		try {
			pacienteService.atualizaRegistro(dadosPaciente, id);
		} catch (RegistroNotFoundException e) {
			return new ResponseEntity<>("Paciente não encontrado", HttpStatus.BAD_REQUEST);
		} catch (InvalidFieldsException e) {
			return new ResponseEntity<>("Campos inválidos", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeMedico(@PathVariable Long id) {
		try {
			pacienteService.removeRegistro(id);
		} catch (RegistroNotFoundException e) {
			return new ResponseEntity<>("Paciente não encontrado", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
