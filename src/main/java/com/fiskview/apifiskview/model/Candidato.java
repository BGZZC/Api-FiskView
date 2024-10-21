package com.fiskview.apifiskview.model;

import jakarta.persistence.*;

@Entity
@Table(name = "candidato")
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCandidato;

    @Column(name = "id_campana", nullable = false)
    private Integer idCampana;

    @Column(name = "id_partido", nullable = false)
    private Integer idPartido;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "informacion")
    private String informacion;

    @Lob
    @Column(name = "propuesta")
    private byte[] propuesta;

    public Long getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Long idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Integer getIdCampana() {
        return idCampana;
    }

    public void setIdCampana(Integer idCampana) {
        this.idCampana = idCampana;
    }

    public Integer getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Integer idPartido) {
        this.idPartido = idPartido;
    }

    public String getNombres() {
        return nombre;
    }

    public void setNombres(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public byte[] getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(byte[] propuesta) {
        this.propuesta = propuesta;
    }
}
