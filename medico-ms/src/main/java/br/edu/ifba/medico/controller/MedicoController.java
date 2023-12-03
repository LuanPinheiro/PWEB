package br.edu.ifba.medico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import br.edu.ifba.medico.dtos.MedicoAtualizar;
import br.edu.ifba.medico.dtos.MedicoConsulta;
import br.edu.ifba.medico.dtos.MedicoEnviar;
import br.edu.ifba.medico.dtos.MedicoListar;
import br.edu.ifba.medico.models.Especialidade;
import br.edu.ifba.medico.services.MedicoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;


@RestController
@RequestMapping("/medicos")
@Validated
public class MedicoController {
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<MedicoListar> listarMedicos(@RequestParam(name="page", required=false) Integer page) {
		return medicoService.listarTodos(page);
	}
	
	@GetMapping("/email")
	@ResponseStatus(HttpStatus.OK)
	public Page<MedicoListar> listarMedicosPorEmail(@RequestParam(name="page", required=false) Integer page, @RequestParam(name="email", required = true) String email) {
		return medicoService.listarPorEmail(page, email);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void novoMedico(@RequestBody MedicoEnviar dadosMedico){
		
		medicoService.novoRegistro(dadosMedico);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void atualizaMedico(@Valid @RequestBody MedicoAtualizar dadosMedico){
		
		medicoService.atualizaRegistro(dadosMedico);
	}
	
	@DeleteMapping
	public ResponseEntity<?> removeMedico(@NotBlank(message = "CRM inválido ou nulo") @RequestParam("crm") String crm){
		
		medicoService.removeRegistro(crm);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/encontrarPorCrm")
	public ResponseEntity<MedicoConsulta> encontrarPorCrm(@NotBlank(message = "CRM inválido ou nulo") @RequestParam("crm") String crm){
		
		return new ResponseEntity<>(new MedicoConsulta(medicoService.encontrarPorIdentificador(crm)), HttpStatus.OK);
	}
	
	@GetMapping("/encontrarPorEspecialidade/{especialidade}")
	public ResponseEntity<List<MedicoConsulta>> encontrarPorEspecialidade(@PathVariable Especialidade especialidade){
		
		return new ResponseEntity<>(
				medicoService.medicosPorEspecialidade(especialidade)
					.stream()
					.map((medico) -> new MedicoConsulta(medico)).toList(),
				HttpStatus.OK);
	}
}
