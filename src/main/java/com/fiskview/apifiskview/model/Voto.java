package com.fiskview.apifiskview.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "voto")
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioVotante usuario; // Relación con Usuario

    @ManyToOne
    @JoinColumn(name = "campana_id")
    private Campana campana;

    @ManyToOne
    @JoinColumn(name = "candidato_id")
    private Candidato candidato;

    @Column(name = "codigo_hash", nullable = false, unique = true)
    private String codigoHash;

    @Column(name = "fecha_voto")
    private LocalDateTime fechaVoto;

    // Getters and Setters

    public Long getIdVoto() {
        return id;
    }

    public void setIdVoto(Long id) {
        this.id = id;
    }

    public UsuarioVotante getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioVotante usuario) {
        this.usuario = usuario;
    }

    public Campana getCampana() {
        return campana;
    }

    public void setCampana(Campana campana) {
        this.campana = campana;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
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
}
