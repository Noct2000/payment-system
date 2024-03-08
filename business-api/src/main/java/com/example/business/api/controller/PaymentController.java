package com.example.business.api.controller;

import com.example.business.api.dto.response.PaymentResponseDto;
import com.example.business.api.service.PaymentFeignClientService;
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

    @GetMapping("/by-inn/{inn}")
    List<PaymentResponseDto> getByPayment(@PathVariable String inn) {
        return paymentFeignClientService.getByInn(inn);
    }

    @GetMapping("/by-okpo/{okpo}")
    List<PaymentResponseDto> getByOkpo(@PathVariable String okpo) {
        return paymentFeignClientService.getByOkpo(okpo);
    }
}
