package br.edu.ifba.trabalho.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import br.edu.ifba.trabalho.dtos.MedicoEnviar;
import br.edu.ifba.trabalho.dtos.MedicoListar;
import br.edu.ifba.trabalho.services.MedicoService;


@RestController
@RequestMapping("/medicos")
public class MedicosController {
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<MedicoListar> listarMedicos(@RequestParam(required = false) Integer page) {
		return medicoService.listarTodos(page);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void enviarMedico(@RequestBody MedicoEnviar dadosMedico){
		medicoService.enviarMedico(dadosMedico);
	}
	
	@PutMapping
	@RequestMapping("/{id}")
	public ResponseEntity<?> atualizaMedico(@RequestBody MedicoEnviar medico, @PathVariable(value = "id") Long id) {
		Boolean atualizou = medicoService.atualizaMedico(medico, id);
		if(!atualizou) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping
	@RequestMapping("/{id}")
	public ResponseEntity<?> removeMedico(@PathVariable(value = "id") Long id) {
		Boolean removeu = medicoService.removeMedico(id);
		if(!removeu) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
