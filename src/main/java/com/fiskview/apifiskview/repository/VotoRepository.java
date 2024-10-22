package com.fiskview.apifiskview.repository;

import com.fiskview.apifiskview.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    List<Voto> findByUsuarioId(Long idUsuario); // Obtener votos por ID de usuario
    List<Voto> findByCampana_Id(Long campanaId); // Obtener votos por ID de campaña
    List<Voto> findByCandidatoId(Long candidatoId);
}
