package com.fiskview.apifiskview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VotoResponseDTO {
    private LocalDateTime fechaVoto;
    private String hashVoto;
}
