package com.bni.test.backendfitness.modules.users.controller;

import com.bni.test.backendfitness.helpers.WebResponse;
import com.bni.test.backendfitness.modules.users.dto.UpdateUserPasswordRequest;
import com.bni.test.backendfitness.modules.users.dto.UpdateUserRequest;
import com.bni.test.backendfitness.modules.users.dto.UserResponse;
import com.bni.test.backendfitness.modules.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping(
            path = "/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> current() throws Exception {
        UserResponse current = userService.getCurrent();

        return WebResponse.<UserResponse>builder()
                .data(current)
                .build();
    }

    @PutMapping(
            path = "/update/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> updateUser(@PathVariable("userId") String userId ,@RequestBody UpdateUserRequest request) throws Exception {
        UserResponse response = userService.updateUser(userId, request);

        return WebResponse.<UserResponse>builder().data(response).build();
    }

    @PatchMapping(
            path = "/update-password/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> updatePassword(@PathVariable("userId") String userId ,@RequestBody UpdateUserPasswordRequest request) {
        userService.updatePassword(userId, request);

        return WebResponse.<String>builder().data("OK").build();
    }
}
