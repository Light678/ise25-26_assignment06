package de.seuhd.campuscoffee.api.controller;

import de.seuhd.campuscoffee.api.dtos.UserDto;
import de.seuhd.campuscoffee.api.mapper.UserDtoMapper;
import de.seuhd.campuscoffee.domain.ports.UserDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserDataService userDataService;
    private final UserDtoMapper userDtoMapper;

    /**
     * GET /api/users
     * Abrufen aller Benutzer.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userDataService.getAll().stream()
                .map(userDtoMapper::toDto)
                .toList();
    }

    /**
     * GET /api/users/{id}
     * Abrufen eines Benutzers nach ID.
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userDtoMapper.toDto(userDataService.getById(id));
    }

    /**
     * GET /api/users/filter?login_name=...
     * Abrufen eines Benutzers nach Login-Name (analog PosController-Filter).
     */
    @GetMapping("/filter")
    public UserDto getUserByLoginName(@RequestParam("login_name") String loginName) {
        return userDtoMapper.toDto(userDataService.getByLoginName(loginName));
    }

    /**
     * POST /api/users
     * Erstellen von Benutzern.
     * Wie bei POS wird eine Liste von DTOs erwartet/zurückgegeben.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserDto> createUsers(@RequestBody @Valid List<UserDto> userDtos) {
        return userDtos.stream()
                // IDs und Timestamps bei Erstellung ignorieren
                .map(dto -> dto.toBuilder()
                        .id(null)
                        .createdAt(null)
                        .updatedAt(null)
                        .build())
                .map(userDtoMapper::toDomain)
                .map(userDataService::upsert)   // id == null -> create
                .map(userDtoMapper::toDto)
                .toList();
    }

    /**
     * PUT /api/users/{id}
     * Aktualisieren eines bestehenden Benutzers.
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id,
                              @RequestBody @Valid UserDto userDto) {

        // ID aus Pfad verwenden, Timestamps vom Domain-Layer setzen lassen
        UserDto normalizedDto = userDto.toBuilder()
                .id(id)
                .createdAt(null)
                .updatedAt(null)
                .build();

        return userDtoMapper.toDto(
                userDataService.upsert(
                        userDtoMapper.toDomain(normalizedDto)
                )
        );
    }

    /**
     * DELETE /api/users/{id}
     * Löschen eines Benutzers nach ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userDataService.delete(id);
    }
}


