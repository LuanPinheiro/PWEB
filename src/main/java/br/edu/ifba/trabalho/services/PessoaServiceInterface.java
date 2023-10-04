package br.edu.ifba.trabalho.services;

import java.util.List;

import br.edu.ifba.trabalho.exceptions.InvalidFieldsException;
import br.edu.ifba.trabalho.exceptions.RegistroNotFoundException;

public interface PessoaServiceInterface<Tabela, DtoEnviar, DtoListar, DtoAtualizar> {
	public List<DtoListar> converteLista(List<Tabela> lista);
	public List<DtoListar> listarTodos(Integer page);
	public void novoRegistro(DtoEnviar dados);
	public void removeRegistro(Long id) throws RegistroNotFoundException;
	public void atualizaRegistro(DtoAtualizar dados, Long id) throws RegistroNotFoundException, InvalidFieldsException;
}
