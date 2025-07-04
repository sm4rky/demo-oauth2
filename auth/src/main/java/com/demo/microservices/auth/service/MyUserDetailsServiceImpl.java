package com.demo.microservices.auth.service;

import com.demo.microservices.auth.entity.User;
import com.demo.microservices.auth.entity.UserPrincipal;
import com.demo.microservices.auth.mapper.UserMapper;
import com.demo.microservices.auth.producer.EmailProducer;
import com.demo.microservices.auth.repository.UserRepository;
import com.demo.microservices.common.dto.EmailRequest;
import com.demo.microservices.common.dto.UserRequest;
import com.demo.microservices.common.dto.UserResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MyUserDetailsServiceImpl implements MyUserDetailsService{
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        return UserPrincipal.from(user);

    }
}
