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

import br.edu.ifba.trabalho.dtos.MedicoEnviar;
import br.edu.ifba.trabalho.dtos.MedicoListar;
import br.edu.ifba.trabalho.exceptions.InvalidFieldsException;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;
import br.edu.ifba.trabalho.services.MedicoService;


@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<MedicoListar> listarMedicos(@RequestParam(required = false) Integer page) {
		return medicoService.listarTodos(page);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void novoMedico(@RequestBody MedicoEnviar dadosMedico){
		medicoService.novoRegistro(dadosMedico);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizaMedico(@RequestBody MedicoEnviar dadosMedico, @PathVariable Long id) {
		try {
			medicoService.atualizaRegistro(dadosMedico, id);
		} catch (RegistroNotFoundException e) {
			return new ResponseEntity<>("Medico não encontrado", HttpStatus.BAD_REQUEST);
		} catch (InvalidFieldsException e) {
			return new ResponseEntity<>("Campos inválidos", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeMedico(@PathVariable Long id) {
		try {
			medicoService.removeRegistro(id);
		} catch (RegistroNotFoundException e) {
			return new ResponseEntity<>("Medico não encontrado", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
}
