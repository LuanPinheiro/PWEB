package br.edu.ifba.medico.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.medico.models.Especialidade;
import br.edu.ifba.medico.models.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{
	public Optional<Medico> findByIdAndAtivoTrue(Long id);
	public List<Medico> findByEspecialidadeAndAtivoTrue(Especialidade especialidade);
	public Page<Medico> findByAtivoTrue(Pageable pageable);
	public Optional<Medico> findByCrm(String crm);
}
