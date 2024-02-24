package com.bni.test.backendfitness.modules.users.service;

import com.bni.test.backendfitness.base.constant.GlobalConstant;
import com.bni.test.backendfitness.helpers.GlobalHelper;
import com.bni.test.backendfitness.modules.users.dto.UpdateUserPasswordRequest;
import com.bni.test.backendfitness.modules.users.dto.UpdateUserRequest;
import com.bni.test.backendfitness.modules.users.dto.UserResponse;
import com.bni.test.backendfitness.modules.users.entity.User;
import com.bni.test.backendfitness.modules.users.repository.UserRepository;
import com.bni.test.backendfitness.security.jwt.UserCurrentUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.bni.test.backendfitness.base.constant.GlobalConstant.CONSTANT_USER_NOT_FOUND;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponse getCurrent() throws Exception {
        User user = userRepository.findById(UserCurrentUtils.getCurrentUserId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, CONSTANT_USER_NOT_FOUND)
        );

        return toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, CONSTANT_USER_NOT_FOUND)
        );

        user.setName(request.getName());
        user.setCardNumber(GlobalHelper.toEncrypt(request.getCardNumber()));
        user.setCcv(GlobalHelper.toEncrypt(request.getCcv()));
        user.setCardHolderName(GlobalHelper.toEncrypt(request.getCardHolderName()));
        user.setExpiredDate(GlobalHelper.toEncrypt(request.getExpiredDate()));

        userRepository.save(user);

        return toUserResponse(user);
    }

    public void updatePassword(String userId, UpdateUserPasswordRequest request){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, CONSTANT_USER_NOT_FOUND)
        );

        if (!passwordEncoder.matches(request.getPasswordOld(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password Old Mistake");

        user.setPassword(passwordEncoder.encode(request.getPasswordNew()));

        userRepository.save(user);
    }

    private UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus().toString())
                .phoneNumber(user.getPhoneNumber())
                .cardHolderName(GlobalHelper.toDecrypt(user.getCardHolderName()))
                .cardNumber(GlobalHelper.toDecrypt(user.getCardNumber()))
                .ccv(GlobalHelper.toDecrypt(user.getCcv()))
                .expiredDate(GlobalHelper.toDecrypt(user.getExpiredDate()))
                .build();
    }
}
