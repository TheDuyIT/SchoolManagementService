package com.apollogix.demo.mapper;

import com.apollogix.demo.domain.UserInfo;
import com.apollogix.web.rest.model.AuthenticationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterRequestMapper {
    UserInfo toUserInfo(AuthenticationRequest source);
}
