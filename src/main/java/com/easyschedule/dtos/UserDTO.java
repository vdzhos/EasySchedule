package com.easyschedule.dtos;

import com.easyschedule.annotations.PasswordMatches;
import com.easyschedule.annotations.RoleCodeMatches;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@PasswordMatches
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Getter
    @Setter
    @NotBlank(message = "Login must not be blank")
    @Pattern(regexp = "^(?=.{4,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "Login doesn't match the pattern!")
    private String login;

    @Getter
    @Setter
    @NotBlank(message = "Password must not be blank")
    @Size(min = 4, message = "Password must be at least 4 characters long!")
    @Size(max = 20, message = "Password must be no longer than 4 characters!")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Password must contain only digits, latin letters, dots and underscore characters!")
    private String password;

    @Getter
    @Setter
    private String matchingPassword;

    @Getter
    @Setter
    @NotBlank(message = "Role code must not be blank")
    @RoleCodeMatches
    private String roleCode;

}