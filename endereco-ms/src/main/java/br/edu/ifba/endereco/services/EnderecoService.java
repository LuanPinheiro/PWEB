package br.edu.ifba.endereco.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.endereco.dtos.EnderecoDTO;
import br.edu.ifba.endereco.models.Endereco;
import br.edu.ifba.endereco.repositories.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Long encontraPorDto(EnderecoDTO endereco) {
		// Busca se o endereco passado pelo cliente já existe no banco para não gerar tupla
		Endereco enderecoFinal = enderecoRepository
				.findByLogradouroAndNumeroAndComplementoAndBairroAndCidadeAndUfAndCep(
						endereco.logradouro(),
						endereco.numero(),
						endereco.complemento(),
						endereco.bairro(),
						endereco.cidade(),
						endereco.uf(),
						endereco.cep())
				// Se o endereço não foi encontrado cria no banco novo registro de endereço
				.orElseGet(() -> new Endereco(endereco));
		
		enderecoRepository.save(enderecoFinal);
		return enderecoFinal.getId();
	}
}
