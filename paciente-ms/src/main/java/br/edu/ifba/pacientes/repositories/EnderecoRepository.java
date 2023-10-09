package br.edu.ifba.pacientes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.pacientes.models.Endereco;

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
