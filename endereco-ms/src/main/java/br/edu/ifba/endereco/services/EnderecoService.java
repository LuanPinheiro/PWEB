package br.edu.ifba.endereco.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.endereco.dtos.EnderecoDTO;
import br.edu.ifba.endereco.exceptions.EnderecoNotFoundException;
import br.edu.ifba.endereco.models.Endereco;
import br.edu.ifba.endereco.repositories.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public EnderecoDTO encontraPorDto(EnderecoDTO endereco) {
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
		return new EnderecoDTO(enderecoFinal);
	}

	public EnderecoDTO encontraPorId(Long id) throws EnderecoNotFoundException {
		Optional<Endereco> enderecoFinal = enderecoRepository.findById(id);
		if(enderecoFinal.isEmpty()) {
			throw new EnderecoNotFoundException();
		}
		return new EnderecoDTO(enderecoFinal.get());
	}
}
