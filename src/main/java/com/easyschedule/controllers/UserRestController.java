package com.easyschedule.controllers;

import com.easyschedule.dtos.UserDTO;
import com.easyschedule.dtos.UserPutDTO;
import com.easyschedule.exceptions.user.UserAccessForbiddenException;
import com.easyschedule.models.User;
import com.easyschedule.services.interfaces.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(@NotBlank @RequestParam String login, @NotBlank @RequestParam String password) throws Exception {
        return ResponseEntity.ok(getUserAndCheck(login, password));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDTO dto) throws Exception {
        return new ResponseEntity<>(userService.addUser(dto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@NotBlank @RequestParam String login, @NotBlank @RequestParam String password) throws Exception {
        getUserAndCheck(login, password);
        return ResponseEntity.ok("User " + login + " deleted");
    }

    @PutMapping
    public ResponseEntity<User> updatePassword(@Valid @RequestBody UserPutDTO dto) throws Exception {
        return ResponseEntity.ok(userService.updateUser(dto));

    }

    private User getUserAndCheck(String login, String password) throws Exception {
        User user = userService.getUser(login);
        if(!user.getPassword().equals(password))
            throw new UserAccessForbiddenException("wrong password");
        return user;
    }

}
