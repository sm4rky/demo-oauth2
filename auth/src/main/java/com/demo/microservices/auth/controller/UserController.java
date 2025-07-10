package com.demo.microservices.auth.controller;

import com.demo.microservices.auth.entity.User;
import com.demo.microservices.auth.entity.UserPrincipal;
import com.demo.microservices.auth.mapper.UserMapper;
import com.demo.microservices.auth.service.JwtService;
import com.demo.microservices.auth.service.UserService;
import com.demo.microservices.common.dto.*;
import com.demo.microservices.common.entity.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserMapper userMapper;
    UserService userService;
    JwtService jwtService;
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody UserRequest request) {
        User saved = userService.save(userMapper.toUser(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<UserResponse>builder()
                        .message("User registered successfully")
                        .data(userMapper.toUserResponse(saved))
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        String accessToken  = jwtService.generateAccessToken(principal);
        long expires = jwtService.getExpirySeconds();
        String refreshToken = jwtService.generateRefreshToken(principal);

        AuthResponse authResponse = new AuthResponse(
                accessToken,
                expires,
                refreshToken,
                "Bearer");
        return ResponseEntity.ok()
                .body(ApiResponse.<AuthResponse>builder()
                        .message("User login successfully")
                        .data(authResponse)
                        .build());
    }

    @PutMapping("/verify")
    public ResponseEntity<ApiResponse<?>> verify(@RequestParam String token) {
        userService.verifyUser(token);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Account verified successfully")
                .build());
    }

    @PostMapping("/password/reset/token")
    public ResponseEntity<ApiResponse<?>> generateResetPasswordToken(@RequestParam String email) {
        userService.generateResetPasswordToken(email);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Token generated successfully")
                .build());
    }

    @GetMapping("/password/reset/verify")
    public ResponseEntity<ApiResponse<?>> verifyResetPasswordToken(@RequestParam String token) {
        userService.verifyResetPasswordToken(token);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Token verified successfully")
                .build());
    }

    @PutMapping("/password/reset")
    public ResponseEntity<ApiResponse<?>> resetPasswordToken(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Password reset successfully")
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> findAllUsers() {
        return ResponseEntity.ok()
                .body(ApiResponse.<List<UserResponse>>builder()
                        .message("Users retrieved successfully")
                        .data(userService.findAll().stream()
                                .map(userMapper::toUserResponse)
                                .toList())
                        .build());
    }
}
