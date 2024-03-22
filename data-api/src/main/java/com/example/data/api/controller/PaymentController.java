package com.example.data.api.controller;

import com.example.data.api.dto.request.CreatePaymentRequestDto;
import com.example.data.api.dto.request.UpdatePaymentRequestDto;
import com.example.data.api.dto.response.PaymentResponseDto;
import com.example.data.api.mapper.PaymentMapper;
import com.example.data.api.model.Payment;
import com.example.data.api.service.DebitingService;
import com.example.data.api.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(
        name = "Payment",
        description = "endpoints for CRUD operations for Payment model"
)
public class PaymentController {
    private final PaymentService paymentService;
    private final DebitingService debitingService;
    private final PaymentMapper paymentMapper;

    @PostMapping
    @Operation(
            summary = "create new payment",
            description = "creates new payment "
                    + "with first active bankTransaction "
                    + "with debiting",
            tags = {"Payment"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = PaymentResponseDto.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input or impossible operation",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE
                    )
            )
    })
    public PaymentResponseDto create(
            @Parameter(
                    required = true,
                    name = "createPaymentRequestDto"
            )
            @RequestBody
            @Valid
            CreatePaymentRequestDto createPaymentRequestDto
    ) {
        Payment payment = debitingService.createPaymentWithFirstTransactionAndDebiting(
                paymentMapper.toModel(createPaymentRequestDto)
        );
        return paymentMapper.toResponseDto(payment);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "get payment by id",
            description = "gets persisted payment by paymentId",
            tags = {"Payment"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = PaymentResponseDto.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE
                    )
            )
    })
    public PaymentResponseDto getById(
            @Parameter(
                    required = true,
                    name = "paymentId"
            )
            @PathVariable
            Long id
    ) {
        Payment payment = paymentService.getById(id);
        return paymentMapper.toResponseDto(payment);
    }

    @GetMapping("/by-inn/{inn}")
    @Operation(
            summary = "get all payments by INN",
            description = "gets all payments by INN",
            tags = {"Payment"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = List.class
                                    )
                            )
                    }
            )
    })
    public List<PaymentResponseDto> getByInn(
            @Parameter(
                    required = true,
                    name = "INN"
            )
            @PathVariable
            String inn
    ) {
        List<Payment> payments = paymentService.findAllByInn(inn);
        return payments.stream().map(paymentMapper::toResponseDto).toList();
    }

    @GetMapping("/by-okpo/{okpo}")
    @Operation(
            summary = "get all payments by OKPO",
            description = "gets all payments by OKPO",
            tags = {"Payment"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = List.class
                                    )
                            )
                    }
            )
    })
    public List<PaymentResponseDto> getByOkpo(
            @Parameter(
                    required = true,
                    name = "OKPO"
            )
            @PathVariable
            String okpo
    ) {
        List<Payment> payments = paymentService.findAllByOkpo(okpo);
        return payments.stream().map(paymentMapper::toResponseDto).toList();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "delete payment by id",
            description = "deletes persisted payment by paymentId "
                    + "without debiting operations",
            tags = {"Payment"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = Boolean.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input or impossible operation",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE
                    )
            )
    })
    public ResponseEntity<Boolean> deleteById(
            @Parameter(
                    required = true,
                    name = "paymentId"
            )
            @PathVariable
            Long id
    ) {
        paymentService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping
    @Operation(
            summary = "update payment",
            description = "updates persisted payment "
                    + "without debiting operations",
            tags = {"Payment"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = PaymentResponseDto.class
                                    )
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input or impossible operation",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE
                    )
            )
    })
    public PaymentResponseDto update(
            @Parameter(
                    required = true,
                    name = "updatePaymentRequestDto"
            )
            @RequestBody
            @Valid
            UpdatePaymentRequestDto updatePaymentRequestDto
    ) {
        Payment payment = paymentService.update(paymentMapper.toModel(updatePaymentRequestDto));
        return paymentMapper.toResponseDto(payment);
    }
}
