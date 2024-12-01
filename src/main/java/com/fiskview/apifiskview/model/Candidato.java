package com.fiskview.apifiskview.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidato")
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "propuesta", columnDefinition = "TEXT")
    private String propuesta;



    @Override
    public String toString() {
        return "Candidato{" +
                "id=" + id +
                ", idCampana=" + idCampana +
                ", idPartido=" + idPartido +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", informacion='" + informacion + '\'' +
                ", propuesta='" + propuesta + '\'' +
                '}';
    }
}
