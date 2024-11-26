package com.fiskview.apifiskview.service;

import com.fiskview.apifiskview.dto.BlockDTO;
import com.fiskview.apifiskview.dto.TransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractVoteService {

    private final Web3j web3j;

    @Value("${web3j.contract.address}")
    private String contractAddress;

    @Value("${web3j.contract.private-key}")
    private String privateKey;

    public static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(6_721_975L);


    public TransactionReceipt vote(Long campanaId, Long candidatoId, Long usuarioId) throws TransactionException,
            IOException {
        // Validar credenciales
        Credentials credentials = Credentials.create(privateKey);
        TransactionManager txManager = new RawTransactionManager(web3j, credentials);

        Function function = new Function(
                "vote",
                Arrays.asList(
                        new Uint256(BigInteger.valueOf(campanaId)),
                        new Uint256(BigInteger.valueOf(candidatoId)),
                        new Uint256(BigInteger.valueOf(usuarioId))
                ),
                Collections.emptyList()
        );
        String txData = FunctionEncoder.encode(function);
        EthSendTransaction ethSendTransaction = txManager.sendTransaction(
                GAS_PRICE,
                GAS_LIMIT,
                contractAddress,
                txData,
                BigInteger.ZERO
        );
        if (ethSendTransaction.hasError()) {
            throw new RuntimeException("Error enviando la transacción: " + ethSendTransaction.getError().getMessage());
        }
        String transactionHash = ethSendTransaction.getTransactionHash();
        PollingTransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor(
                web3j,
                TransactionManager.DEFAULT_POLLING_FREQUENCY,
                TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH
        );
        return receiptProcessor.waitForTransactionReceipt(transactionHash);
    }


    public Boolean hasUserVoted(Long electionId, Long usuarioId) throws IOException {
        Function function = new Function(
                "hasUserVoted",
                Arrays.asList(
                        new Uint256(BigInteger.valueOf(electionId)),
                        new Uint256(BigInteger.valueOf(usuarioId))
                ),
                List.of(new TypeReference<Bool>() {
                })
        );

        EthCall response = web3j.ethCall(
                org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(
                        contractAddress, contractAddress, FunctionEncoder.encode(function)),
                org.web3j.protocol.core.DefaultBlockParameterName.LATEST
        ).send();
        List<Type> result = FunctionReturnDecoder.decode(response.getResult(), function.getOutputParameters());
        return (Boolean) result.get(0).getValue();
    }

    public List<BlockDTO> listarBloquesYTransacciones() throws Exception {
        BigInteger ultimoBloque = web3j.ethBlockNumber().send().getBlockNumber();
        List<BlockDTO> bloques = new ArrayList<>();

        for (BigInteger i = BigInteger.ZERO; i.compareTo(ultimoBloque) <= 0; i = i.add(BigInteger.ONE)) {
            EthBlock ethBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(i), true).send();
            EthBlock.Block bloque = ethBlock.getBlock();
            BlockDTO blockDTO = new BlockDTO();
            blockDTO.setBlockNumber(bloque.getNumber());
            blockDTO.setBlockHash(bloque.getHash());
            blockDTO.setTimestamp(bloque.getTimestamp());
            blockDTO.setMiner(bloque.getMiner());
            blockDTO.setTransactionCount(bloque.getTransactions().size());
            List<TransactionDTO> transactionDTOs = new ArrayList<>();
            for (EthBlock.TransactionResult txResult : bloque.getTransactions()) {
                EthBlock.TransactionObject tx = (EthBlock.TransactionObject) txResult.get();

                TransactionDTO transactionDTO = new TransactionDTO();
                transactionDTO.setTransactionHash(tx.getHash());
                transactionDTO.setFrom(tx.getFrom());
                transactionDTO.setTo(tx.getTo());
                transactionDTO.setValue(tx.getValue());

                transactionDTOs.add(transactionDTO);
            }
            blockDTO.setTransactions(transactionDTOs);

            bloques.add(blockDTO);
        }

        return bloques;
    }




}
