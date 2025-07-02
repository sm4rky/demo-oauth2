package com.demo.microservices.auth.mapper;

import com.demo.microservices.auth.entity.User;
import com.demo.microservices.common.dto.UserRequest;
import com.demo.microservices.common.dto.UserResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-02T11:26:22-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstName( request.firstName() );
        user.lastName( request.lastName() );
        user.email( request.email() );
        user.password( request.password() );

        return user.build();
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        String id = null;
        String firstName = null;
        String lastName = null;
        String email = null;

        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();

        UserResponse userResponse = new UserResponse( id, firstName, lastName, email );

        return userResponse;
    }
}
