package com.example.data.api.controller;

import com.example.data.api.dto.request.CreateBankTransactionRequestDto;
import com.example.data.api.dto.request.UpdateBankTransactionRequestDto;
import com.example.data.api.dto.response.BankTransactionResponseDto;
import com.example.data.api.mapper.BankTransactionMapper;
import com.example.data.api.model.BankTransaction;
import com.example.data.api.service.BankTransactionService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Tag(
        name = "BankTransaction",
        description = "endpoints for CRUD operations for BankTransaction model"
)
public class BankTransactionController {
    private final BankTransactionService bankTransactionService;
    private final BankTransactionMapper bankTransactionMapper;

    @PostMapping
    @Operation(
            summary = "create new bankTransaction",
            description = "creates active bankTransaction with debiting",
            tags = {"BankTransaction"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = BankTransactionResponseDto.class
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
    public BankTransactionResponseDto create(
            @Parameter(
                    required = true,
                    name = "createBankTransactionRequestDto"
            )
            @RequestBody
            @Valid
            CreateBankTransactionRequestDto createBankTransactionRequestDto
    ) {
        BankTransaction bankTransaction = bankTransactionService.createWithDebitingByPaymentId(
                createBankTransactionRequestDto.getPaymentId()
        );
        return bankTransactionMapper.toResponseDto(bankTransaction);
    }

    @PutMapping
    @Operation(
            summary = "update persisted bankTransaction",
            description = "update persisted bankTransaction without debiting operations",
            tags = {"BankTransaction"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = BankTransactionResponseDto.class
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
    public BankTransactionResponseDto update(
            @Parameter(
                    required = true,
                    name = "updateBankTransactionRequestDto"
            )
            @RequestBody
            @Valid
            UpdateBankTransactionRequestDto updateBankTransactionRequestDto
    ) {
        BankTransaction bankTransaction = bankTransactionService
                .updateByRequestDto(updateBankTransactionRequestDto);
        return bankTransactionMapper.toResponseDto(bankTransaction);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "delete persisted bankTransaction",
            description = "delete persisted bankTransaction without debiting operations",
            tags = {"BankTransaction"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Boolean.class)
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
    public ResponseEntity<Boolean> deleteById(
            @Parameter(
                    required = true,
                    name = "bankTransactionId"
            )
            @PathVariable
            Long id
    ) {
        bankTransactionService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/payments/{id}")
    @Operation(
            summary = "get all bankTransactions by paymentId",
            description = "get list of all undeleted bankTransactions by paymentId",
            tags = {"BankTransaction"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = List.class)
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
    public List<BankTransactionResponseDto> getByPaymentId(
            @Parameter(
                    required = true,
                    name = "paymentId"
            )
            @PathVariable(name = "id")
            Long paymentId
    ) {
        List<BankTransaction> bankTransactions = bankTransactionService
                .findAllByPaymentId(paymentId);
        return bankTransactions.stream().map(bankTransactionMapper::toResponseDto).toList();
    }

    @PatchMapping("/rollback/{id}")
    @Operation(
            summary = "rollback active bankTransaction by id",
            description = "rollback active bankTransaction by id "
                    + "with rollback money transfer operations",
            tags = {"BankTransaction"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = BankTransactionResponseDto.class
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
    public BankTransactionResponseDto rollbackTransaction(
            @Parameter(
                    required = true,
                    name = "bankTransactionId"
            )
            @PathVariable Long id
    ) {
        BankTransaction bankTransaction = bankTransactionService.rollbackById(id);
        return bankTransactionMapper.toResponseDto(bankTransaction);
    }

    @GetMapping("/last-with-unique-payment-id")
    @Operation(
            summary = "get last transaction for each payment",
            description = "get last transaction for each payment",
            tags = {"BankTransaction"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = List.class)
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
    public List<BankTransactionResponseDto> getLastWithUniquePaymentId() {
        List<BankTransaction> bankTransactions = bankTransactionService
                .getLastWithUniquePaymentId();
        return bankTransactions.stream().map(bankTransactionMapper::toResponseDto).toList();
    }
}
