package br.edu.ifba.clinicamonolith.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.clinicamonolith.models.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	public Optional<Paciente> findByIdAndAtivoTrue(Long id);
	public Page<Paciente> findByAtivoTrue(Pageable pageable);
}
