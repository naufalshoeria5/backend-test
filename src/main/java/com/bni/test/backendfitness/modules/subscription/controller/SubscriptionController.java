package com.bni.test.backendfitness.modules.subscription.controller;

import com.bni.test.backendfitness.helpers.WebResponse;
import com.bni.test.backendfitness.modules.subscription.dto.CreateSubscriptionRequest;
import com.bni.test.backendfitness.modules.subscription.dto.SubscriptionPaymentResponse;
import com.bni.test.backendfitness.modules.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> get(){
        subscriptionService.get();

        return WebResponse.<String>builder().data("OK").build();
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<SubscriptionPaymentResponse> create(@RequestBody CreateSubscriptionRequest request){
        SubscriptionPaymentResponse data = subscriptionService.createData(request);

        return WebResponse.<SubscriptionPaymentResponse>builder().data(data).build();
    }
}
