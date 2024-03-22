package com.example.data.api.controller;

import com.example.data.api.config.DataApiPostgresContainer;
import com.example.data.api.dto.request.CreatePaymentRequestDto;
import com.example.data.api.dto.response.PaymentResponseDto;
import com.example.data.api.model.BankAccount;
import com.example.data.api.service.BankAccountService;
import com.example.data.api.service.BankTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BankTransactionService bankTransactionService;

    public static final DataApiPostgresContainer postgreSQLContainer = new DataApiPostgresContainer();

    static {
        postgreSQLContainer.start();
    }

    @BeforeEach
    void setUp() {
        BankAccount firstBankAccount = new BankAccount()
                .setId(1L)
                .setFullName("KVASHA OLEH")
                .setIban("UA683052490263056400954301111")
                .setAccountNumber("5168745038101111")
                .setAmount(BigDecimal.valueOf(1000L));

        BankAccount secondBankAccount = new BankAccount()
                .setId(2L)
                .setFullName("KVASHA SERGEY")
                .setIban("UA683052490263056400954302222")
                .setAccountNumber("5168745038102222")
                .setAmount(BigDecimal.valueOf(1000L));
        bankAccountService.save(firstBankAccount);
        bankAccountService.save(secondBankAccount);
    }

    @Test
    @SneakyThrows
    void savePaymentWithFirstBankTransaction_ok() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON_VALUE);
        ClassPathResource requestDto = new ClassPathResource("payment_create_request_dto.json");
        ClassPathResource responseDto = new ClassPathResource("payment_response_after_single_create.json");
        CreatePaymentRequestDto createPaymentRequestDto = objectMapper
                .readValue(requestDto.getFile(), CreatePaymentRequestDto.class);
        HttpEntity<CreatePaymentRequestDto> httpEntity = new HttpEntity<>(
                createPaymentRequestDto,
                httpHeaders
        );

        ResponseEntity<PaymentResponseDto> responseEntity = testRestTemplate.exchange(
                "/payments",
                HttpMethod.POST,
                httpEntity,
                PaymentResponseDto.class
        );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PaymentResponseDto paymentResponseDto = objectMapper
                .readValue(responseDto.getFile(), PaymentResponseDto.class);
        assertEquals(paymentResponseDto, responseEntity.getBody());

        int actualCountOfBankTransactions = bankTransactionService
                .findAllByPaymentId(paymentResponseDto.getId())
                .size();

        assertEquals(1, actualCountOfBankTransactions);
    }
}
