package com.fiskview.apifiskview.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransactionDTO {
    private String transactionHash;
    private String from;
    private String to;
    private BigInteger value;
}
