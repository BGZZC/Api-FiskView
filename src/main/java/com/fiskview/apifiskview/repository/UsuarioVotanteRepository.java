package com.fiskview.apifiskview.repository;

import com.fiskview.apifiskview.model.UsuarioVotante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioVotanteRepository extends JpaRepository<UsuarioVotante, Long> {

    boolean existsByEmail(String email);

    Optional<UsuarioVotante> findByEmail(String email);
}
