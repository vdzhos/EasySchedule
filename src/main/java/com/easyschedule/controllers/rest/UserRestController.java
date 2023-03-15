package com.easyschedule.controllers.rest;

import com.easyschedule.dtos.UserDTO;
import com.easyschedule.dtos.UserPutDTO;
import com.easyschedule.exceptions.user.UserAccessForbiddenException;
import com.easyschedule.models.User;
import com.easyschedule.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(@NotBlank @RequestParam String login, @NotBlank @RequestParam String password) {
        return ResponseEntity.ok(getUserAndCheck(login, password));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDTO dto) {
        return new ResponseEntity<>(userService.addUser(dto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@NotBlank @RequestParam String login, @NotBlank @RequestParam String password) {
        getUserAndCheck(login, password);
        return ResponseEntity.ok("User " + login + " deleted");
    }

    @PutMapping
    public ResponseEntity<User> updatePassword(@Valid @RequestBody UserPutDTO dto) {
        return ResponseEntity.ok(userService.updateUser(dto));
    }

    private User getUserAndCheck(String login, String password) {
        User user = userService.getUser(login);
        if (!user.getPassword().equals(password))
            throw new UserAccessForbiddenException("wrong password");
        return user;
    }

}
