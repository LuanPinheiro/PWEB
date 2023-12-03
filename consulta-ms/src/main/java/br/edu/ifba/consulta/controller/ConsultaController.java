package br.edu.ifba.consulta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.consulta.dtos.ConsultaCancelar;
import br.edu.ifba.consulta.dtos.ConsultaEnviar;
import br.edu.ifba.consulta.services.ConsultaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
	
	@Autowired
	private ConsultaService consultaService;
	
	@GetMapping
	public ResponseEntity<?> listarConsultas(@RequestParam(required = false) String crm, @RequestParam(required = false) String cpf, @RequestParam(required = false) Integer page){
		String tabela = null;
		String parametro = null;
		
		if((crm == null && cpf == null) || (crm != null && cpf != null)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(crm != null) {
			tabela = "medico";
			parametro = crm;
		}
		else {
			tabela = "paciente";
			parametro = cpf;
		}
		
		return new ResponseEntity<>(consultaService.listarConsultas(tabela, parametro, page), HttpStatus.OK);
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
