package com.demo.microservices.auth.controller;

import com.demo.microservices.auth.entity.User;
import com.demo.microservices.auth.entity.UserPrincipal;
import com.demo.microservices.auth.mapper.UserMapper;
import com.demo.microservices.auth.service.UserService;
import com.demo.microservices.common.dto.AuthResponse;
import com.demo.microservices.common.dto.LoginRequest;
import com.demo.microservices.common.dto.UserRequest;
import com.demo.microservices.common.dto.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserMapper userMapper;
    UserService userService;
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {
        User saved = userService.save(userMapper.toUser(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toUserResponse(saved));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        AuthResponse authResponse = new AuthResponse(
                "",
                0L,
                "",
                "",
                userMapper.toUserResponse(principal));
        return ResponseEntity.ok()
                .body(authResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return ResponseEntity.ok()
                .body(userService.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList());
    }
}
