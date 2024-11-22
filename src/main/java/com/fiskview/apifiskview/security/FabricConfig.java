package com.fiskview.apifiskview.security;

import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class FabricConfig {

    @Bean
    public HFClient hfClient() throws Exception {
        // Cargar los certificados, claves y configuración de la red
        NetworkConfig networkConfig = NetworkConfig.fromYamlFile(new File("network-connection-profile.yaml"));
        HFClient client = HFClient.createNewInstance();

        // Configurar la red y el canal
        client.setCryptoSuite(CryptoSuite.Factory.getCryptoSuite());
        client.setUserContext(new UserContext()); // Establece un usuario para la conexión
        return client;
    }
}