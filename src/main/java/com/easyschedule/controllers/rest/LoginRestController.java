package com.easyschedule.controllers.rest;

import com.easyschedule.dtos.UserDTO;
import com.easyschedule.exceptions.user.InvalidPasswordException;
import com.easyschedule.exceptions.user.LoginUsedException;
import com.easyschedule.exceptions.user.UserNotFoundException;
import com.easyschedule.models.User;
import com.easyschedule.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;


@RestController
@RequestMapping("/api/login")
public class LoginRestController {

    @Autowired
    private UserService loginService;
    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@NotBlank @RequestBody Map<String, String> params) throws UserNotFoundException, InvalidPasswordException {
        if(!params.containsKey("login") || !params.containsKey("password")) {{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }}
        String login = params.get("login");
        String password = params.get("password");
        User user = loginService.getUser(login);
        if(!user.getPassword().equals(password))
            throw new InvalidPasswordException("Invalid password");
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());

        Authentication authentication = authManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return new ResponseEntity<>(Map.of("message","You have successfully logged in"), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody UserDTO userData) throws LoginUsedException
    {
        loginService.addUser(userData);
        return new ResponseEntity<>(Map.of("message","Success: New user registered"), HttpStatus.CREATED);
    }

}
