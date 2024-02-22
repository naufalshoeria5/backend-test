package com.bni.test.backendfitness.modules.auth.service;

import com.bni.test.backendfitness.base.baseEnum.EStatus;
import com.bni.test.backendfitness.base.response.JwtResponse;
import com.bni.test.backendfitness.base.service.EmailService;
import com.bni.test.backendfitness.modules.auth.dto.*;
import com.bni.test.backendfitness.modules.users.entity.User;
import com.bni.test.backendfitness.modules.users.repository.UserRepository;
import com.bni.test.backendfitness.security.BCrypt;
import com.bni.test.backendfitness.security.jwt.JwtUtils;
import com.bni.test.backendfitness.security.services.UserDetailsImpl;
import com.bni.test.backendfitness.utils.AesEncrytionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Transactional
    public void register(RegisterUserRequest userRequest) throws Exception {
        User user = new User();

        if (userRepository.findFirstByEmail(userRequest.getEmail()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already register");

        Random random = new Random();
        String otp = String.valueOf(random.nextInt(10000));

        user.setId(UUID.randomUUID().toString());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()));
        user.setPhoneNumber(userRequest.getPhoneNumber());

        user.setCardNumber(this.toEncrypt(userRequest.getCardNumber()));
        user.setCcv(this.toEncrypt(userRequest.getCcv()));
        user.setExpiredDate(this.toEncrypt(userRequest.getExpiredDate()));
        user.setCardHolderName(this.toEncrypt(userRequest.getCardHolderName()));
        user.setStatus(EStatus.UNVALIDATED);
        user.setOtpCode(this.toEncrypt(otp));
        userRepository.save(user);

        emailService.sendSimpleMessage(user.getEmail(),"OTP FITNESS",otp);
    }

    @Transactional
    public void validateUser(ValidateRegisterRequest request) throws Exception {
        User user = userRepository.findFirstByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
        );

        if (user.getStatus().equals(EStatus.REGISTERED))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Has Been Validate");

        if (!this.toDecrypt(user.getOtpCode()).equals(request.getOtpCode()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validate Failed");

        user.setOtpCode(null);
        user.setStatus(EStatus.REGISTERED);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public CheckEmailResponse checkEmail(String email){
        Optional<User> user = userRepository.findFirstByEmail(email);
        CheckEmailResponse checkEmailResponse = new CheckEmailResponse();
        checkEmailResponse.setEmail(email);

        if (user.isPresent()){
            checkEmailResponse.setStatus(user.get().getStatus().toString());
        }else{
            checkEmailResponse.setStatus(EStatus.UNREGISTERED.toString());
        }

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

    public void forgotPassword(String email) throws Exception {
        User user = userRepository.findFirstByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
        );

        Random random = new Random();
        String otp = String.valueOf(random.nextInt(10000));

        user.setOtpCode(this.toEncrypt(otp));

        userRepository.save(user);

        emailService.sendSimpleMessage(user.getEmail(),"OTP FORGOT PASSWORD",otp);
    }

    public void updatePassword(UpdatePasswordRequest request) throws Exception {
        User user = userRepository.findFirstByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")
        );

        if (!this.toDecrypt(user.getOtpCode()).equals(request.getOtpCode()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validate Failed");

        user.setOtpCode(null);
        user.setPassword(request.getPassword());
        userRepository.save(user);
    }

    private String toEncrypt(String encrypt) throws Exception {
        return AesEncrytionUtil.encrypt(encrypt);
    }

    private String toDecrypt(String encrypt) throws Exception {
        return AesEncrytionUtil.decrypt(encrypt);
    }
}
