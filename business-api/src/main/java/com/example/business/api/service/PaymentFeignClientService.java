package com.example.business.api.service;

import com.example.business.api.dto.response.PaymentResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment", url = "${data-api}")
public interface PaymentFeignClientService {
    @GetMapping("/payments/by-inn/{inn}")
    List<PaymentResponseDto> getByInn(@PathVariable String inn);

    @GetMapping("/payments/by-okpo/{okpo}")
    List<PaymentResponseDto> getByOkpo(@PathVariable String okpo);
}
