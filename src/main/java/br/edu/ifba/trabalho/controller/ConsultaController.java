package br.edu.ifba.trabalho.controller;

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

import br.edu.ifba.trabalho.dtos.ConsultaCancelar;
import br.edu.ifba.trabalho.dtos.ConsultaEnviar;
import br.edu.ifba.trabalho.dtos.ConsultaListar;
import br.edu.ifba.trabalho.exceptions.DataInvalidaException;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;
import br.edu.ifba.trabalho.services.ConsultaService;

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
	public ResponseEntity<?> marcarConsulta(@RequestBody ConsultaEnviar dados){
		try {
			consultaService.marcarConsulta(dados);
		} catch (RegistroNotFoundException e) {
			return new ResponseEntity<>("Registro não encontrado", HttpStatus.BAD_REQUEST);
		} catch (DataInvalidaException e) {
			return new ResponseEntity<>("Data inválida", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping
	public ResponseEntity<?> cancelarConsulta(@RequestBody ConsultaCancelar dados){
		try {
			consultaService.cancelarConsulta(dados);
		} catch (RegistroNotFoundException e) {
			return new ResponseEntity<>("Registro não encontrado", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);

	}
}
