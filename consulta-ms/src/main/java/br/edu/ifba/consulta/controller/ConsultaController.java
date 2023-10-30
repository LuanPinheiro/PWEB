package br.edu.ifba.consulta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<?> marcarConsulta(@Valid @RequestBody ConsultaEnviar dados){
		
		consultaService.marcarConsulta(dados);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping
	public ResponseEntity<?> cancelarConsulta(@Valid @RequestBody ConsultaCancelar dados){
		
		consultaService.cancelarConsulta(dados);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

	}
}
