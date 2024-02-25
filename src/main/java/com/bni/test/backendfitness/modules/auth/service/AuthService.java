package com.bni.test.backendfitness.modules.auth.service;

import com.bni.test.backendfitness.base.baseenum.EStatus;
import com.bni.test.backendfitness.base.constant.GlobalConstant;
import com.bni.test.backendfitness.base.response.JwtResponse;
import com.bni.test.backendfitness.base.service.EmailService;
import com.bni.test.backendfitness.helpers.GlobalHelper;
import com.bni.test.backendfitness.modules.auth.dto.*;
import com.bni.test.backendfitness.modules.users.entity.User;
import com.bni.test.backendfitness.modules.users.repository.UserRepository;
import com.bni.test.backendfitness.security.jwt.JwtUtils;
import com.bni.test.backendfitness.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static com.bni.test.backendfitness.base.constant.GlobalConstant.CONSTANT_USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterUserRequest userRequest){
        User user = new User();

        if (userRepository.findFirstByEmail(userRequest.getEmail()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already register");

        Random random = new Random();
        String otp = String.valueOf(random.nextInt(10000));

        user.setId(UUID.randomUUID().toString());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhoneNumber(userRequest.getPhoneNumber());

        user.setCardNumber(GlobalHelper.toEncrypt(userRequest.getCardNumber()));
        user.setCcv(GlobalHelper.toEncrypt(userRequest.getCcv()));
        user.setExpiredDate(GlobalHelper.toEncrypt(userRequest.getExpiredDate()));
        user.setCardHolderName(GlobalHelper.toEncrypt(userRequest.getCardHolderName()));
        user.setStatus(EStatus.UNVALIDATED);
        user.setOtpCode(GlobalHelper.toEncrypt(otp));
        user.setOtpExpiredAt(GlobalHelper.localDateTimeAdd(60,null));
        userRepository.save(user);

        emailService.sendSimpleMessage(user.getEmail(),"OTP FITNESS",otp);
    }

    @Transactional
    public void otpRegister(String email) {
        Random random = new Random();
        String otp = String.valueOf(random.nextInt(10000));
        User user = userRepository.findFirstByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, CONSTANT_USER_NOT_FOUND)
        );

        if (user.getStatus().equals(EStatus.REGISTERED))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Has Been Validate");

        user.setOtpCode(GlobalHelper.toEncrypt(otp));
        user.setOtpExpiredAt(GlobalHelper.localDateTimeAdd(60,null));

        emailService.sendSimpleMessage(user.getEmail(),"OTP FITNESS",otp);

        userRepository.save(user);
    }

    @Transactional
    public void validateUser(ValidateRegisterRequest request) {
        User user = userRepository.findFirstByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, CONSTANT_USER_NOT_FOUND)
        );

        if (user.getStatus().equals(EStatus.REGISTERED))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Has Been Validate");

        if (!GlobalHelper.toDecrypt(user.getOtpCode()).equals(request.getOtpCode()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validate Failed");

        if (LocalDateTime.now().isAfter(user.getOtpExpiredAt()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP Expired");

        user.setOtpCode(null);
        user.setOtpExpiredAt(null);
        user.setStatus(EStatus.REGISTERED);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public CheckEmailResponse checkEmail(String email){
        User user = userRepository.findFirstByEmail(email).orElse(null);
        CheckEmailResponse checkEmailResponse = new CheckEmailResponse();
        checkEmailResponse.setEmail(email);

        checkEmailResponse.setStatus(user != null ? user.getStatus().toString() : EStatus.UNREGISTERED.toString());

        return checkEmailResponse;
    }

    public JwtResponse login(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return JwtResponse.builder()
                .token(jwt)
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .type("bearer")
                .build();
    }

    public void forgotPassword(String email){
        User user = userRepository.findFirstByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, CONSTANT_USER_NOT_FOUND)
        );

        Random random = new Random();
        String otp = String.valueOf(random.nextInt(10000));

        user.setOtpCode(GlobalHelper.toEncrypt(otp));

        userRepository.save(user);

        emailService.sendSimpleMessage(user.getEmail(),"OTP FORGOT PASSWORD",otp);
    }

    public void updatePassword(UpdatePasswordRequest request) {
        User user = userRepository.findFirstByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, CONSTANT_USER_NOT_FOUND)
        );

        if (Objects.isNull(user.getOtpCode()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You Don't have otp, please click forgot password");

        if (!GlobalHelper.toDecrypt(user.getOtpCode()).equals(request.getOtpCode()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validate Failed");

        user.setOtpCode(null);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }


}
