package com.example.springdemo1705.controller;

import com.example.springdemo1705.model.UserRequestDto;
import com.example.springdemo1705.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201
    public void createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.createUser(userRequestDto);
    }

    @PostMapping("/{userId}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void addRoleToUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "ADMIN") String role) {
        userService.addRoleToUser(userId, role);
    }

}
