package br.edu.ifba.pacientes.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.pacientes.models.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	public Optional<Paciente> findByCpfAndAtivoTrue(String cpf);
	public Page<Paciente> findByAtivoTrue(Pageable pageable);
	public Optional<Paciente> findByCpf(String cpf);
}
