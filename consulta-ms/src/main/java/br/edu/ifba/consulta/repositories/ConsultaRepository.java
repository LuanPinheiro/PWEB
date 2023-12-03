package br.edu.ifba.consulta.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifba.consulta.models.Consulta;
import br.edu.ifba.consulta.models.ConsultaId;
import br.edu.ifba.consulta.models.Motivo;
import jakarta.transaction.Transactional;

public interface ConsultaRepository extends JpaRepository<Consulta, ConsultaId> {

	public Optional<Consulta> findByIds(ConsultaId ids);
	public Optional<Consulta> findByIdsDataAndIdsPacienteIdAndDesmarcadoFalse(LocalDate data, String pacienteId);
	public Optional<Consulta> findByIdsMedicoIdAndIdsDataAndIdsHoraAndDesmarcadoFalse(String crm, LocalDate data, LocalTime hora);
	public Page<Consulta> findByIdsMedicoIdAndDesmarcadoFalse(String crm, Pageable pageable);
	public Page<Consulta> findByIdsPacienteIdAndDesmarcadoFalse(String cpf, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query("update consultas c set c.desmarcado = TRUE, c.motivo = :motivo where c.ids.pacienteId = :id and c.desmarcado = false")
	public void cancelarPacienteDesativado(@Param("id")Long id, @Param("motivo")Motivo motivo);
	
	@Modifying
	@Transactional
	@Query("update consultas c set c.desmarcado = TRUE, c.motivo = :motivo where c.ids.medicoId = :id and c.desmarcado = false")
	public void cancelarMedicoDesativado(@Param("id")Long id, @Param("motivo")Motivo motivo);
	
}
