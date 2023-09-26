package br.edu.ifba.trabalho.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.trabalho.dtos.MedicoEnviar;
import br.edu.ifba.trabalho.dtos.MedicoListar;
import br.edu.ifba.trabalho.models.Medico;
import br.edu.ifba.trabalho.services.MedicoService;


@RestController
@RequestMapping("/medicos")
public class MedicosController {
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping
	public ResponseEntity<List<MedicoListar>> listarMedicos() {
		List<MedicoListar> medicos = medicoService.listarTodos();
		return new ResponseEntity<List<MedicoListar>>(medicos, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> enviarMedico(@RequestBody MedicoEnviar dadosMedico){
		Medico medico = medicoService.enviarMedico(dadosMedico);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
