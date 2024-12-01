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
@Table(name = "usuario_votante")
public class UsuarioVotante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "dni")
    private String dni;


    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;


    @Column(name = "email")
    private String email;


    @Column(name = "password")
    private String password;


    @Column(name = "estado")
    private Integer estado; // Estado del usuario (1 = activo, 0 = inactivo)

    @Column(name = "imagen_facial", columnDefinition = "TEXT")
    private String imagen_facial;


    @Override
    public String toString() {
        return "UsuarioVotante{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", estado=" + estado +
                ", imagen_facial='" + imagen_facial + '\'' +
                '}';
    }
}
