package com.fiskview.apifiskview.repository;

import com.fiskview.apifiskview.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findByIdPartido(Long idPartido);

    Candidato findByNombre(String nombre);
}
