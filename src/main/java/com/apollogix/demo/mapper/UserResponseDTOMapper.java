package com.apollogix.demo.mapper;

import com.apollogix.demo.domain.UserInfo;
import com.apollogix.web.rest.model.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseDTOMapper {
    UserInfo toUserInfo(UserResponseDTO source);

    UserResponseDTO toUserResponse(UserInfo source);
}
