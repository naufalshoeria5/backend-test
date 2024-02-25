package com.bni.test.backendfitness.modules.payment.repository;

import com.bni.test.backendfitness.modules.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findFirstByPaymentCode(String paymentCode);
}
