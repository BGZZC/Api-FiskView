package com.fiskview.apifiskview.repository;

import com.fiskview.apifiskview.model.UsuarioVotante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioVotanteRepository extends JpaRepository<UsuarioVotante, Long> {
    Optional<UsuarioVotante> findById(Long id);
    boolean existsByEmail(String email);
    UsuarioVotante findByDni(String dni);

    Optional<UsuarioVotante> findByEmail(String email);
}
