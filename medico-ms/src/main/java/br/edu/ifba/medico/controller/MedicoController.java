package br.edu.ifba.medico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import br.edu.ifba.medico.dtos.MedicoAtualizar;
import br.edu.ifba.medico.dtos.MedicoConsulta;
import br.edu.ifba.medico.dtos.MedicoEnviar;
import br.edu.ifba.medico.dtos.MedicoListar;
import br.edu.ifba.medico.exceptions.InvalidFieldsException;
import br.edu.ifba.medico.exceptions.RegistroNotFoundException;
import br.edu.ifba.medico.models.Especialidade;
import br.edu.ifba.medico.models.Medico;
import br.edu.ifba.medico.services.MedicoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<MedicoListar> listarMedicos(@RequestParam("page") int page) {
		final Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "dadosPessoais.nome"));
		return medicoService.listarTodos(pageable);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void novoMedico(@Valid @RequestBody MedicoEnviar dadosMedico){
		
		medicoService.novoRegistro(dadosMedico);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizaMedico(
			@NotNull @Valid @RequestBody MedicoAtualizar dadosMedico,
			@PathVariable Long id) 
				throws RegistroNotFoundException, InvalidFieldsException {
		
		medicoService.atualizaRegistro(dadosMedico, id);
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeMedico(@PathVariable Long id) 
			throws RegistroNotFoundException {
		
		medicoService.removeRegistro(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/encontrarPorId/{id}")
	public ResponseEntity<MedicoConsulta> encontrarPorId(@PathVariable Long id) 
			throws RegistroNotFoundException {
		
		Medico medico = medicoService.encontrarPorId(id);
		
		return new ResponseEntity<>(new MedicoConsulta(medico), HttpStatus.OK);
	}
	
	@GetMapping("/encontrarPorEspecialidade/{especialidade}")
	public ResponseEntity<List<MedicoConsulta>> encontrarPorEspecialidade(@PathVariable Especialidade especialidade) 
			throws RegistroNotFoundException {
		
		List<MedicoConsulta> medicos = medicoService.medicosPorEspecialidade(especialidade)
										.stream()
										.map((medico) -> new MedicoConsulta(medico)).toList();
		
		return new ResponseEntity<>(medicos, HttpStatus.OK);
	}
}
