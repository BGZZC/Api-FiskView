package com.fiskview.apifiskview.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class BlockDTO {
    private BigInteger blockNumber;
    private String blockHash;
    private BigInteger timestamp;
    private String miner;
    private int transactionCount;
    private List<TransactionDTO> transactions;
}
