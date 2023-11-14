package br.edu.ifba.medico.services;

import org.springframework.data.domain.Page;

import br.edu.ifba.medico.exceptions.InvalidFieldsException;
import br.edu.ifba.medico.exceptions.RegistroExistenteException;
import br.edu.ifba.medico.exceptions.RegistroNotFoundException;

/**
 * Interface para padronizar as operações de CRUD de entidades que sejam pessoas
 * */
public interface PessoaServiceInterface<Tabela, DtoEnviar, DtoListar, DtoAtualizar> {
	/**
	 * Lista todos os registros onde ativo = true
	 * */
	public Page<DtoListar> listarTodos(Integer page);
	
	/**
	 * Gera um novo registro no banco caso não seja uma tupla
	 * */
	public void novoRegistro(DtoEnviar dados) throws RegistroExistenteException;
	
	/**
	 * Apaga um registro dado o campo identificador na tabela caso ele exista e ativo = true
	 * */
	public void removeRegistro(String identificador) throws RegistroNotFoundException;
	
	/**
	 * Atualiza um registro dado o campo identificador na tabela e caso ativo = true
	 * */
	public void atualizaRegistro(DtoAtualizar dados) throws RegistroNotFoundException, InvalidFieldsException;
	
	/**
	 * Encontra um registro dado o campo identificador na tabela caso ele exista e ativo = true
	 * */
	public Tabela encontrarPorIdentificador(String identificador) throws RegistroNotFoundException;
	
	/**
	 * Valida os campos do DTO de atualização, retornando falha em caso de enviar um campo inválido
	 * */
	public void validaCamposDto(DtoAtualizar dto) throws InvalidFieldsException;
}
