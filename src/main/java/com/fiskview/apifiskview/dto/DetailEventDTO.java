package com.fiskview.apifiskview.dto;

import com.fiskview.apifiskview.model.Candidato;
import com.fiskview.apifiskview.model.UsuarioVotante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DetailEventDTO {
    private String transactionHash;
    private Candidato candidato;
    private UsuarioVotante usuario;
    private LocalDateTime fecha;
}
