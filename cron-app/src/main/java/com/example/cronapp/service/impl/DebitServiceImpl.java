package com.example.cronapp.service.impl;

import com.example.cronapp.dto.CreateBankTransactionRequestDto;
import com.example.cronapp.dto.PaymentResponseDto;
import com.example.cronapp.service.DebitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class DebitServiceImpl implements DebitService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    @Qualifier("transactionRequestBuilder")
    private final HttpRequest.Builder transactionRequestBuilder;
    @Qualifier("paymentsToTransactionRequest")
    private final HttpRequest paymentsToTransactionRequest;

    @Override
    public void createTransactionForPaymentsToDebiting() {
        List<PaymentResponseDto> payments = getPaymentsToTransaction();
        createTransactions(payments);
    }

    private void createTransactions(List<PaymentResponseDto> payments) {
        for (PaymentResponseDto payment : payments) {
            try {
                HttpRequest request = transactionRequestBuilder
                        .POST(HttpRequest.BodyPublishers.ofString(
                        objectMapper.writeValueAsString(
                                new CreateBankTransactionRequestDto(payment.getId()))
                        )
                ).build();
                String responseBody = httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                ).body();
                log.info("Create new Transaction: {}", responseBody);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(
                        "Can't create CreateBankTransactionRequestDto for payment: "
                                + payment
                );
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<PaymentResponseDto> getPaymentsToTransaction() {
        String jsonResponse = "";
        try {
            jsonResponse = httpClient.send(
                    paymentsToTransactionRequest,
                    HttpResponse.BodyHandlers.ofString()
            ).body();
            List<PaymentResponseDto> payments = objectMapper.readValue(
                    jsonResponse,
                    new TypeReference<List<PaymentResponseDto>>() { }
            );
            log.info("Got payments: {}", jsonResponse);
            return payments;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Can't create list of PaymentResponseDto for jsonResponse: "
                            + jsonResponse
            );
        } catch (IOException | InterruptedException e) {
            log.warn("Can't get payments to transaction, error: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
