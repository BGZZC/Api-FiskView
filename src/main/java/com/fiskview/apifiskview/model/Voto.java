package com.fiskview.apifiskview.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "voto")
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
