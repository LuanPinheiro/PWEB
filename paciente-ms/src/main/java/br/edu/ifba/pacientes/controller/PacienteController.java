package br.edu.ifba.pacientes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import br.edu.ifba.pacientes.dtos.PacientaConsulta;
import br.edu.ifba.pacientes.dtos.PacienteAtualizar;
import br.edu.ifba.pacientes.dtos.PacienteEnviar;
import br.edu.ifba.pacientes.dtos.PacienteListar;
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
	public Page<PacienteListar> listarPacientes(@RequestParam(name="page", required=false) Integer page) {
		return pacienteService.listarTodos(page);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void novoPaciente(@Valid @RequestBody PacienteEnviar dadosPaciente){
		pacienteService.novoRegistro(dadosPaciente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizaPaciente(
			@NotNull @Valid @RequestBody PacienteAtualizar dadosPaciente,
			@PathVariable Long id){
		
		pacienteService.atualizaRegistro(dadosPaciente, id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removePaciente(@PathVariable Long id){
		
		pacienteService.removeRegistro(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/encontrarPorId/{id}")
	public ResponseEntity<PacientaConsulta> encontrarPorId(@PathVariable Long id){
		
		return new ResponseEntity<>(new PacientaConsulta(pacienteService.encontrarPorId(id)),HttpStatus.OK);
	}
}
