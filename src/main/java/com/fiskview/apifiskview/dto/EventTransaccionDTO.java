package com.fiskview.apifiskview.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventTransaccionDTO {
    private String transaccionId;
    private long userId;
    private long candidateId;
}
