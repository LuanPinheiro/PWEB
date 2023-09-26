package br.edu.ifba.trabalho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.trabalho.models.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

}
