package com.easyschedule.models;

import com.easyschedule.utils.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Getter
    @Setter
    @Id
    @Column(name = "Login", nullable = false)
    private String login;

    @Getter
    @Setter
    @NotBlank(message = "Password must not be blank")
    @Size(min = 4, message = "Password must be at least 4 characters long!")
    @Size(max = 20, message = "Password must be no longer than 4 characters!")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Password must contain only digits, latin letters, dots and underscore characters!")
    @Column(name = "Password", nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(name = "Role", nullable = false)
    private Role role;

    public User(User that) {
        this.login = that.getLogin();
        this.password = that.getPassword();
        this.role = that.getRole();
    }

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "login = " + this.login + "\\ " +
                "password = " + this.password + "\\ " +
                "role = " + this.role.name() + '}';
    }

}

