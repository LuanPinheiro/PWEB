package br.edu.ifba.trabalho.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.trabalho.models.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	public Optional<Paciente> findByIdAndAtivoTrue(Long id);
}
