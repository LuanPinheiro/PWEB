package br.edu.ifba.trabalho.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifba.trabalho.exceptions.InvalidFieldsException;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;

public interface PessoaServiceInterface<Tabela, DtoEnviar, DtoListar, DtoAtualizar> {
	public Page<DtoListar> listarTodos(Pageable pageable);
	public void novoRegistro(DtoEnviar dados);
	public void removeRegistro(Long id) throws RegistroNotFoundException;
	public void atualizaRegistro(DtoAtualizar dados, Long id) throws RegistroNotFoundException, InvalidFieldsException;
	public Tabela encontrarPorId(Long id) throws RegistroNotFoundException;
	public void validaCamposDto(DtoAtualizar dto) throws InvalidFieldsException;
}
