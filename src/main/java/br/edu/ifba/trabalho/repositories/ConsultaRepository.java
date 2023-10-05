package br.edu.ifba.trabalho.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.trabalho.models.Consulta;
import br.edu.ifba.trabalho.models.ConsultaId;

public interface ConsultaRepository extends JpaRepository<Consulta, ConsultaId> {

	public Consulta findByIds(ConsultaId ids);
	public List<Consulta> findByIdsDataAndIdsPacienteId(LocalDate data, Long pacienteId);

}
