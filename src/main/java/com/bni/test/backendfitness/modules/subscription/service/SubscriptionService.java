package com.bni.test.backendfitness.modules.subscription.service;

import com.bni.test.backendfitness.base.baseenum.EPaymentStatus;
import com.bni.test.backendfitness.base.baseenum.EStatusSubscription;
import com.bni.test.backendfitness.base.service.EmailService;
import com.bni.test.backendfitness.modules.menu.enitity.Menu;
import com.bni.test.backendfitness.modules.menu.repository.MenuRepository;
import com.bni.test.backendfitness.modules.payment.entity.Payment;
import com.bni.test.backendfitness.modules.payment.repository.PaymentRepository;
import com.bni.test.backendfitness.modules.subscription.dto.CreateSubscriptionRequest;
import com.bni.test.backendfitness.modules.subscription.dto.SubscriptionResponse;
import com.bni.test.backendfitness.modules.subscription.entity.Subscription;
import com.bni.test.backendfitness.modules.subscription.repository.SubscriptionRepository;
import com.bni.test.backendfitness.modules.users.entity.User;
import com.bni.test.backendfitness.modules.users.repository.UserRepository;
import com.bni.test.backendfitness.security.jwt.UserCurrentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final EmailService emailService;

    public SubscriptionResponse get(){
        return new SubscriptionResponse();
    }
    public void createData(CreateSubscriptionRequest request){
        Menu menu = menuRepository.findById(request.getMenuId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu Not Found")
        );

        User user = userRepository.findById(UserCurrentUtils.getCurrentUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
        );

        Random random = new Random();
        int otp = random.nextInt(1000);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        LocalDateTime localDateTime = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();


        Subscription subscription = new Subscription();
        subscription.setId(UUID.randomUUID().toString());
        subscription.setMenu(menu);
        subscription.setTotal(menu.getTotal());
        subscription.setUser(user);
        subscription.setStatus(EStatusSubscription.WAITING);

        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setDate(LocalDate.now());
        payment.setStatus(EPaymentStatus.WAITING);

        payment.setSubscription(subscription);
        payment.setMethod(request.getPaymentMethod());
        payment.setOtpCode(Integer.toString(otp));
        payment.setExpiredOtp(localDateTime);

        Payment save = paymentRepository.save(payment);

        emailService.sendSimpleMessage(user.getEmail(),"OTP PAYMENT",save.getOtpCode());
    }

}
