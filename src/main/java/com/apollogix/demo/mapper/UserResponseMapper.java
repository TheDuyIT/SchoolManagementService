package com.apollogix.demo.mapper;

import com.apollogix.demo.domain.UserInfo;
import com.apollogix.web.rest.model.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper {
    UserInfo toUserInfo(UserResponseMapper source);
    UserResponseDTO toUserResponse(UserInfo source);
}
