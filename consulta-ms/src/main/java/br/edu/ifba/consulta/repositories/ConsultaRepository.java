package br.edu.ifba.consulta.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.consulta.models.Consulta;
import br.edu.ifba.consulta.models.ConsultaId;

public interface ConsultaRepository extends JpaRepository<Consulta, ConsultaId> {

	public Optional<Consulta> findByIdsAndDesmarcadoFalse(ConsultaId ids);
	public Optional<Consulta> findByIdsDataAndIdsPacienteIdAndDesmarcadoFalse(LocalDate data, Long pacienteId);
	public Optional<Consulta> findByIdsMedicoIdAndIdsDataAndIdsHoraAndDesmarcadoFalse(Long medicoId, LocalDate data, LocalTime hora);
}
