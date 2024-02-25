package com.bni.test.backendfitness.modules.payment.service;

import com.bni.test.backendfitness.base.baseenum.EPaymentStatus;
import com.bni.test.backendfitness.base.baseenum.EStatusSubscription;
import com.bni.test.backendfitness.helpers.GlobalHelper;
import com.bni.test.backendfitness.modules.payment.dto.PaymentVerificationRequest;
import com.bni.test.backendfitness.modules.payment.entity.Payment;
import com.bni.test.backendfitness.modules.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public void validatePayment(PaymentVerificationRequest request){
        Payment payment = paymentRepository.findFirstByPaymentCode(request.getPaymentCode()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Payment Code Not Found")
        );

        if (LocalDateTime.now().isAfter(payment.getExpiredOtp()) || !GlobalHelper.toDecrypt(payment.getOtpCode()).equals(request.getOtpCode()))
            payment.setStatus(EPaymentStatus.FAILED);
        else
            payment.getSubscription().setStatus(EStatusSubscription.ACTIVE);

        paymentRepository.save(payment);
    }
}
