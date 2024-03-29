package com.easyschedule.models;

import com.easyschedule.utils.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

