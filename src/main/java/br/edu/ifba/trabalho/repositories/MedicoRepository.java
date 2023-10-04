package br.edu.ifba.trabalho.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.trabalho.models.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

	public List<Medico> findAllByAtivoTrueOrderByDadosPessoaisNomeAsc(PageRequest of);
}
