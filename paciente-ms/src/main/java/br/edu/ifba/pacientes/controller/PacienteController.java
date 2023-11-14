package br.edu.ifba.pacientes.controller;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.pacientes.dtos.PacienteAtualizar;
import br.edu.ifba.pacientes.dtos.PacienteConsulta;
import br.edu.ifba.pacientes.dtos.PacienteEnviar;
import br.edu.ifba.pacientes.dtos.PacienteListar;
import br.edu.ifba.pacientes.services.PacienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pacientes")
@Validated
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
	
	@PutMapping
	public ResponseEntity<?> atualizaPaciente(@Valid @RequestBody PacienteAtualizar dadosPaciente){
		
		pacienteService.atualizaRegistro(dadosPaciente);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping
	public ResponseEntity<?> removePaciente(@CPF @RequestBody String cpf){
		
		pacienteService.removeRegistro(cpf);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/encontrarPorCpf")
	public ResponseEntity<PacienteConsulta> encontrarPorCpf(@CPF @RequestParam("cpf") String cpf){
		
		return new ResponseEntity<>(new PacienteConsulta(pacienteService.encontrarPorIdentificador(cpf)),HttpStatus.OK);
	}
}
