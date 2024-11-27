package com.fiskview.apifiskview.repository;

import com.fiskview.apifiskview.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
  //  List<Voto> findByUsuarioId(Long id_usuario); // Obtener votos por ID de usuario
  //  List<Voto> findByCampana_Id(Long campanaId); // Obtener votos por ID de campaña
  //  List<Voto> findByCandidatoId(Long candidatoId);
  boolean existsByCodigoHash(String codigoHash);
    @Procedure(name="f_insertar_voto")
    public String f_insertar_voto(
            @Param("in_idusuario") int id_usuario,
            @Param("in_idcampana") int campana_id,
            @Param("in_idcandidato") int candidato_id,
            @Param("in_codigo_hash") String codigo_hash
    );
}
