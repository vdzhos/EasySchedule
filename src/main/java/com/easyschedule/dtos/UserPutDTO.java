package com.easyschedule.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPutDTO {
    @NotBlank(message = "Login must not be blank")
    @Pattern(regexp = "^(?=.{4,15}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "Login doesn't match the pattern!")
    private String login;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 4, message = "Password must be at least 4 characters long!")
    @Size(max = 20, message = "Password must be no longer than 4 characters!")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Password must contain only digits, latin letters, dots and underscore characters!")
    private String password;

    @NotBlank(message = "New password must not be blank")
    @Size(min = 4, message = "Password must be at least 4 characters long!")
    @Size(max = 20, message = "Password must be no longer than 4 characters!")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Password must contain only digits, latin letters, dots and underscore characters!")
    private String newPassword;
}
