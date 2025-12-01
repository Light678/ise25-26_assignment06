package de.seuhd.campuscoffee.api.mapper;

import de.seuhd.campuscoffee.api.dto.UserDto;
import de.seuhd.campuscoffee.domain.model.User;

import java.util.Objects;

public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public UserDto toDto(User user) {
        Objects.requireNonNull(user, "user must not be null");

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setLoginName(user.getLoginName());
        dto.setEmailAddress(user.getEmailAddress());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }

    @Override
    public User toDomain(UserDto dto) {
        Objects.requireNonNull(dto, "dto must not be null");

        return new User(
                dto.getId(),
                dto.getCreatedAt(),
                dto.getUpdatedAt(),
                dto.getLoginName(),
                dto.getEmailAddress(),
                dto.getFirstName(),
                dto.getLastName()
        );
    }
}
