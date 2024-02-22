package com.bni.test.backendfitness.modules.auth.controller;

import com.bni.test.backendfitness.base.response.JwtResponse;
import com.bni.test.backendfitness.helpers.WebResponse;
import com.bni.test.backendfitness.modules.auth.dto.*;
import com.bni.test.backendfitness.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(
            path = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterUserRequest registerUserRequest) throws Exception {
        authService.register(registerUserRequest);

        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }

    @PostMapping(
            path = "/validate/user",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> validateUser(@RequestBody ValidateRegisterRequest validateRegisterRequest) throws Exception {
        authService.validateUser(validateRegisterRequest);

        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }

    @GetMapping(
            path = "/check/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CheckEmailResponse> checkEmail(@PathVariable String email) {
        CheckEmailResponse checkEmailResponse = authService.checkEmail(email);

        return WebResponse.<CheckEmailResponse>builder()
                .data(checkEmailResponse)
                .build();
    }

    @PostMapping(
            path = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<JwtResponse> login(@RequestBody LoginRequest request){
        JwtResponse login = authService.login(request);

        return WebResponse.<JwtResponse>builder()
                .data(login)
                .build();
    }

    @PostMapping(
            path = "/forgot-password",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> forgotPassword(@RequestParam String email) throws Exception {
        authService.forgotPassword(email);

        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }

    @PostMapping(
            path = "/update-password",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> updatePassword(@RequestBody UpdatePasswordRequest request) throws Exception {
        authService.updatePassword(request);

        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }
}
