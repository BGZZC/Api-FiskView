package com.fiskview.apifiskview.service;


import com.fiskview.apifiskview.dto.EventTransaccionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.utils.Numeric;

@Service
@RequiredArgsConstructor
public class EventService {

    private final Web3j web3j;

    @Value("${web3j.contract.address}")
    private String contractAddress;

    public EventTransaccionDTO obtenerDetallesEventoPorTransaccion(String transactionHash) throws Exception {
        // Crear el filtro para los logs
        EthFilter eventFilter = new EthFilter(
                org.web3j.protocol.core.DefaultBlockParameterName.EARLIEST,
                org.web3j.protocol.core.DefaultBlockParameterName.LATEST,
                contractAddress
        );

        EthLog ethLog = web3j.ethGetLogs(eventFilter).send();


        for (EthLog.LogResult logResult : ethLog.getLogs()) {
            EthLog.LogObject log = (EthLog.LogObject) logResult.get();
            if (log.getTransactionHash().equals(transactionHash)) {
                log.getTopics().forEach(System.out::println);
                String userIdHex = log.getTopics().get(3);
                String candidateIdHex = log.getTopics().get(2);

                // Convertir los parámetros de hexadecimal a decimal
                long userId = Numeric.toBigInt(userIdHex).longValue();
                long candidateId = Numeric.toBigInt(candidateIdHex).longValue();
                return EventTransaccionDTO.builder().transaccionId(transactionHash).userId(userId).candidateId(candidateId).build();
            }
        }
        throw new RuntimeException("No existen eventos de ese hash");
    }


}
