package com.fiskview.apifiskview.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidatoResponseDTO {
    private Long idCandidato;
    private Integer idCampana;
    private Integer idPartido;
    private String nombres;
    private String apellidos;
    private String informacion;
    private String propuesta;
}


