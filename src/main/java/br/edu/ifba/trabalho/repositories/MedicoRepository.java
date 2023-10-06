package br.edu.ifba.trabalho.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.trabalho.models.Especialidade;
import br.edu.ifba.trabalho.models.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{
	public Optional<Medico> findByIdAndAtivoTrue(Long id);
	public List<Medico> findByEspecialidade(Especialidade especialidade);
}
