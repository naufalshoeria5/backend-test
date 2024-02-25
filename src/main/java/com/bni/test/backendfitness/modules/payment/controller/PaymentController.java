package com.bni.test.backendfitness.modules.payment.controller;

import com.bni.test.backendfitness.helpers.WebResponse;
import com.bni.test.backendfitness.modules.payment.dto.PaymentVerificationRequest;
import com.bni.test.backendfitness.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public WebResponse<String> validatePayment(@RequestBody PaymentVerificationRequest request){
        paymentService.validatePayment(request);

        return WebResponse.<String>builder().data("OK").build();
    }

}
