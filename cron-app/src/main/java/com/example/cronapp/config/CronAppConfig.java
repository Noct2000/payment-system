package com.example.cronapp.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

@Configuration
public class CronAppConfig {
    @Value("${transaction_endpoint}")
    private String transactionEndpoint;
    @Value("${payments_to_transaction_endpoint}")
    private String paymentsToTransactionEndpoint;

    @Bean
    public HttpClient getHttpClient() {
        return HttpClient.newBuilder().build();
    }

    @Bean(name = "transactionRequestBuilder")
    public HttpRequest.Builder getTransactionRequest() throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(transactionEndpoint))
                .headers("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }

    @Bean(name = "paymentsToTransactionRequest")
    public HttpRequest getPaymentsToTransactionRequest() throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(paymentsToTransactionEndpoint))
                .GET()
                .build();
    }
}
