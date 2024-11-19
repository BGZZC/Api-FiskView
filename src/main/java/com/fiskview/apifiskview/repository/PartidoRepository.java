package com.fiskview.apifiskview.repository;

import com.fiskview.apifiskview.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {
    // Puedes agregar métodos adicionales aquí si es necesario
}
