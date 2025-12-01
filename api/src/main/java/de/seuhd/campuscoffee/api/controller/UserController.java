package de.seuhd.campuscoffee.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "Operations related to user management.")
@Controller
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserDataService userDataService;
    private final UserDtoMapper userDtoMapper;

    /**
     * Create a new user.
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.debug("Creating new user: {}", userDto);

        // ID / Timestamps werden vom Data-Layer gesetzt, daher nullen wir sie zur Sicherheit
        userDto.setId(null);
        userDto.setCreatedAt(null);
        userDto.setUpdatedAt(null);

        User toCreate = userDtoMapper.toDomain(userDto);
        User created = userDataService.upsert(toCreate);
        UserDto response = userDtoMapper.toDto(created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get all users.
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.debug("Retrieving all users");

        List<UserDto> users = userDataService.findAll()
                .stream()
                .map(userDtoMapper::toDto)
                .toList();

        return ResponseEntity.ok(users);
    }

    /**
     * Get a single user by ID.
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        log.debug("Retrieving user with id={}", id);

        return userDataService.findById(id)
                .map(user -> ResponseEntity.ok(userDtoMapper.toDto(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Update an existing user.
     */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserDto userDto
    ) {
        log.debug("Updating user with id={} using payload={}", id, userDto);

        // Sicherstellen, dass die ID aus dem Pfad übernommen wird
        userDto.setId(id);

        User toUpdate = userDtoMapper.toDomain(userDto);
        User updated = userDataService.upsert(toUpdate);
        UserDto response = userDtoMapper.toDto(updated);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete a user by ID.
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("Deleting user with id={}", id);
        userDataService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
