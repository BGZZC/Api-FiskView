package com.fiskview.apifiskview.repository;

import com.fiskview.apifiskview.model.Campana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampanaRepository extends JpaRepository<Campana, Long> {

    // Método para obtener una campaña por su id, si es necesario (aunque JpaRepository ya lo ofrece)
    Optional<Campana> findById(Long id);

    // Puedes agregar más consultas personalizadas si las necesitas, por ejemplo:
    // List<Campana> findByFechaInicioBeforeAndFechaFinAfter(LocalDateTime fecha);
}
