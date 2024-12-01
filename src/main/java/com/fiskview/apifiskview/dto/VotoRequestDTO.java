package com.fiskview.apifiskview.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VotoRequestDTO {
    private long id_usuario;
    private long campana_id;
    private long candidato_id;
}
