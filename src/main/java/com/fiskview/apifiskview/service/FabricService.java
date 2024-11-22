package com.fiskview.apifiskview.service;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Service
public class FabricService {

    private final HFClient hfClient;

    public FabricService(HFClient hfClient) {
        this.hfClient = hfClient;
    }

    public String invokeSmartContract(String chaincodeName, String function, String[] params) throws Exception {
        Channel channel = hfClient.newChannel("mychannel"); // nombre del canal
        channel.initialize();

        TransactionProposalRequest request = hfClient.newTransactionProposalRequest();
        request.setChaincodeName(chaincodeName);
        request.setFcn(function);
        request.setArgs(params);

        // Enviar transacción al canal
        Collection<ProposalResponse> responses = channel.sendTransactionProposal(request);
        for (ProposalResponse response : responses) {
            if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
                // Obtener el payload y convertirlo a String
                byte[] payloadBytes = response.getProposalResponse().getResponse().getPayload().toByteArray();
                return new String(payloadBytes, StandardCharsets.UTF_8); // Convertir de byte[] a String
            } else {
                throw new Exception("Error al invocar el smart contract: " + response.getMessage());
            }
        }
        return "No se obtuvo respuesta satisfactoria";
    }
}
