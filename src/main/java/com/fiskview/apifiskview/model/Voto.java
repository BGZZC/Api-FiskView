package com.fiskview.apifiskview.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "voto")
    @NamedStoredProcedureQueries(value = {
            @NamedStoredProcedureQuery(name = "f_insertar_voto", procedureName = "f_insertar_voto", parameters = {

                    @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_idusuario", type = int.class),
                    @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_idcampana", type = int.class),
                    @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_idcandidato", type = int.class),
                    @StoredProcedureParameter(mode = ParameterMode.IN, name = "in_codigo_hash", type = String.class), }),

    })
    public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "id_usuario", nullable = false)
    private int id_usuario; // Relación con Usuario

    @JoinColumn(name = "campana_id")
    private int campana_id;

    @JoinColumn(name = "candidato_id")
    private int candidato_id;

    @Column(name = "codigo_hash", nullable = false, unique = true)
    private String codigoHash;

    @Column(name = "fecha_voto")
    private LocalDateTime fechaVoto;

    public Voto(Long id, int id_usuario, int campana_id, int candidato_id, String codigoHash, LocalDateTime fechaVoto) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.campana_id = campana_id;
        this.candidato_id = candidato_id;
        this.codigoHash = codigoHash;
        this.fechaVoto = fechaVoto;
    }

    public Voto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getCampana_id() {
        return campana_id;
    }

    public void setCampana_id(int campana_id) {
        this.campana_id = campana_id;
    }

    public int getCandidato_id() {
        return candidato_id;
    }

    public void setCandidato_id(int candidato_id) {
        this.candidato_id = candidato_id;
    }

    public String getCodigoHash() {
        return codigoHash;
    }

    public void setCodigoHash(String codigoHash) {
        this.codigoHash = codigoHash;
    }

    public LocalDateTime getFechaVoto() {
        return fechaVoto;
    }

    public void setFechaVoto(LocalDateTime fechaVoto) {
        this.fechaVoto = fechaVoto;
    }

    @Override
    public String toString() {
        return "Voto{" +
                "id=" + id +
                ", id_usuario=" + id_usuario +
                ", campana_id=" + campana_id +
                ", candidato_id=" + candidato_id +
                ", codigoHash='" + codigoHash + '\'' +
                ", fechaVoto=" + fechaVoto +
                '}';
    }
}
