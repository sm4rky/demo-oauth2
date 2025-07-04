package com.demo.microservices.auth.mapper;

import com.demo.microservices.auth.entity.User;
import com.demo.microservices.auth.entity.UserPrincipal;
import com.demo.microservices.common.dto.UserRequest;
import com.demo.microservices.common.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest request);

    UserResponse toUserResponse(User user);

    UserResponse toUserResponse(UserPrincipal principal);
}
