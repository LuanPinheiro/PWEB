package br.edu.ifba.trabalho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.trabalho.models.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>{
}
