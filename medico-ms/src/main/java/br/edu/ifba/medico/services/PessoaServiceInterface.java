package br.edu.ifba.medico.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.ifba.medico.exceptions.InvalidFieldsException;
import br.edu.ifba.medico.exceptions.RegistroNotFoundException;

public interface PessoaServiceInterface<Tabela, DtoEnviar, DtoListar, DtoAtualizar> {
	/**
	 * Lista todos os registros onde ativo = true
	 * */
	public Page<DtoListar> listarTodos(Pageable pageable);
	
	/**
	 * Gera um novo registro no banco caso não seja uma tupla
	 * */
	public void novoRegistro(DtoEnviar dados);
	
	/**
	 * Apaga um registro dado um id na tabela caso ele exista e ativo = true
	 * */
	public void removeRegistro(Long id) throws RegistroNotFoundException;
	
	/**
	 * Atualiza um registro dado o id na tabela e caso ativo = true
	 * */
	public void atualizaRegistro(DtoAtualizar dados, Long id) throws RegistroNotFoundException, InvalidFieldsException;
	
	/**
	 * Encontra um registro dado o id na tabela caso ele exista e ativo = true
	 * */
	public Tabela encontrarPorId(Long id) throws RegistroNotFoundException;
	
	/**
	 * Valida os campos do DTO de atualização, retornando falha em caso de enviar um campo inválido
	 * */
	public void validaCamposDto(DtoAtualizar dto) throws InvalidFieldsException;
}
