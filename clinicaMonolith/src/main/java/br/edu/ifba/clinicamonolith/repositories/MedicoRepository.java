package br.edu.ifba.clinicamonolith.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.clinicamonolith.models.Especialidade;
import br.edu.ifba.clinicamonolith.models.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{
	public Optional<Medico> findByIdAndAtivoTrue(Long id);
	public List<Medico> findByEspecialidade(Especialidade especialidade);
	public Page<Medico> findByAtivoTrue(Pageable pageable);
}
