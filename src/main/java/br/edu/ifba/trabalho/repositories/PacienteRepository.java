package br.edu.ifba.trabalho.repositories;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.trabalho.models.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	public List<Paciente> findAllByAtivoTrueOrderByNomeAsc(PageRequest of);
}
