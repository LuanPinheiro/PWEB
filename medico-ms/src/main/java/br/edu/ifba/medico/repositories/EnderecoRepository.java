package br.edu.ifba.medico.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.medico.clients.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	
	public Optional<Endereco> findByLogradouroAndNumeroAndComplementoAndBairroAndCidadeAndUfAndCep(
			String logradouro,
			String numero,
			String complemento,
			String bairro,
			String cidade,
			String uf,
			String cep);
}
