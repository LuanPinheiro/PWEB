package br.edu.ifba.trabalho.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.trabalho.models.Consulta;
import br.edu.ifba.trabalho.models.ConsultaId;

public interface ConsultaRepository extends JpaRepository<Consulta, ConsultaId> {

	public Optional<Consulta> findByIds(ConsultaId ids);
	public Optional<Consulta> findByIdsDataAndIdsPacienteId(LocalDate data, Long pacienteId);
	public Optional<Consulta> findByIdsMedicoIdAndIdsHora(Long medicoId, LocalTime hora);
}
