package de.seuhd.campuscoffee.api.controller;

import de.seuhd.campuscoffee.api.dtos.UserDto;
import de.seuhd.campuscoffee.api.mapper.UserDtoMapper;
import de.seuhd.campuscoffee.domain.ports.UserDataService;
import de.seuhd.campuscoffee.domain.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Operations related to user management.")
@Controller
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserDataService userDataService;
    private final UserDtoMapper userDtoMapper;

    /**
     * GET /api/users
     * Abrufen aller Benutzer.
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.debug("Retrieving all users");
        List<UserDto> dtos = userDataService.findAll()
                .stream()
                .map(userDtoMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * GET /api/users/{id}
     * Abrufen eines Benutzers basierend auf einer ID.
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        log.debug("Retrieving user with id={}", id);
        return userDataService.findById(id)
                .map(userDtoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * GET /api/users/filter?login_name=...
     * Abrufen eines Benutzers nach Anmeldename (analog zum PosController).
     */
    @GetMapping("/filter")
    @ResponseBody
    public ResponseEntity<UserDto> getUserByLoginName(
            @RequestParam(name = "login_name") String loginName
    ) {
        log.debug("Retrieving user with loginName={}", loginName);
        return userDataService.findByLoginName(loginName)
                .map(userDtoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/users
     * Erstellen eines neuen Benutzers.
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.debug("Creating new user: {}", userDto);

        // ID und Timestamps beim Erstellen von der DB setzen lassen
        userDto.setId(null);
        userDto.setCreatedAt(null);
        userDto.setUpdatedAt(null);

        User toCreate = userDtoMapper.toDomain(userDto);
        User created = userDataService.upsert(toCreate);

        UserDto responseBody = userDtoMapper.toDto(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    /**
     * PUT /api/users/{id}
     * Aktualisieren eines bestehenden Benutzers.
     */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto userDto
    ) {
        log.debug("Updating user with id={} using {}", id, userDto);

        userDto.setId(id);
        User toUpdate = userDtoMapper.toDomain(userDto);
        User updated = userDataService.upsert(toUpdate);

        UserDto responseBody = userDtoMapper.toDto(updated);
        return ResponseEntity.ok(responseBody);
    }

    /**
     * DELETE /api/users/{id}
     * Löschen eines bestehenden Benutzers.
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("Deleting user with id={}", id);
        userDataService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

