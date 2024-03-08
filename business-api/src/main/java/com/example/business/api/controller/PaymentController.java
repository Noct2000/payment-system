package com.example.business.api.controller;

import com.example.business.api.dto.response.PaymentResponseDto;
import com.example.business.api.service.PaymentFeignClientService;
import com.example.business.api.service.PaymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentFeignClientService paymentFeignClientService;
    private final PaymentService paymentService;

    @GetMapping("/by-inn/{inn}")
    public List<PaymentResponseDto> getByPayment(@PathVariable String inn) {
        return paymentFeignClientService.getByInn(inn);
    }

    @GetMapping("/by-okpo/{okpo}")
    public List<PaymentResponseDto> getByOkpo(@PathVariable String okpo) {
        return paymentFeignClientService.getByOkpo(okpo);
    }

    @GetMapping("/to-transaction")
    public List<PaymentResponseDto> getPaymentsToTransaction() {
        return paymentService.getPaymentsToTransaction();
    }
}
