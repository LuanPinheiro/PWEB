package br.edu.ifba.endereco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.endereco.dtos.EnderecoDTO;
import br.edu.ifba.endereco.exceptions.EnderecoNotFoundException;
import br.edu.ifba.endereco.services.EnderecoService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
	
	@Autowired
	private EnderecoService enderecoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EnderecoDTO gerarEndereco(@Valid @RequestBody EnderecoDTO endereco) {
		return enderecoService.encontraPorDto(endereco);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public EnderecoDTO encontrarEnderecoPorId(@PathVariable Long id) throws EnderecoNotFoundException {
		return enderecoService.encontraPorId(id);
	}
}
