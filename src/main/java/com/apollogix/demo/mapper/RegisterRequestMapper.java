package com.apollogix.demo.mapper;

import com.apollogix.demo.domain.UserInfo;
import com.apollogix.demo.web.model.RegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterRequestMapper {
    UserInfo toUserInfo(RegisterRequest source);
}
