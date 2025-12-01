package de.seuhd.campuscoffee.api.mapper;

import de.seuhd.campuscoffee.api.dto.UserDto;
import de.seuhd.campuscoffee.domain.model.User;

public interface UserDtoMapper {

    UserDto toDto(User user);

    User toDomain(UserDto dto);
}