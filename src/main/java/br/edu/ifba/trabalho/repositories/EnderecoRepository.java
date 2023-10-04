package br.edu.ifba.trabalho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.trabalho.models.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	
	public Endereco findByLogradouroAndNumeroAndComplementoAndBairroAndCidadeAndUfAndCep(
			String logradouro,
			String numero,
			String complemento,
			String bairro,
			String cidade,
			String uf,
			String cep);
}
