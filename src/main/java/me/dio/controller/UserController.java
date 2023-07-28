package me.dio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.domain.model.User;
import me.dio.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/users")
@Tag(name = "Users Controller", description = "RESTful API to manage users.")
public record UserController(UserService userService) {

    @GetMapping
    @Operation(summary = "Get all users.", description = "Retrieve a page of all registered users.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Operation sucessful.")
    }
    )
    public ResponseEntity<Page<User>> findAll(Pageable page) {
        return ResponseEntity.ok(userService.listAll(page));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one user per ID.", description = "Retrieve a single user searching per ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation sucessful."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new user.", description = "Create a new user and returns its data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created sucessfully."),
            @ApiResponse(responseCode = "422", description = "Incorrect user data provided.")
    })
    public ResponseEntity<User> create(@RequestBody User user) {
        var userCreated = userService.create(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(userCreated);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user.", description = "Update a registered user data based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated sucessfully."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "422", description = "Incorrect user data provided.")
    })
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user.", description = "Delete a registered user based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted sucessfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
