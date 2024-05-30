package org.softuni.pathfinder.domain.dtos.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.softuni.pathfinder.validation.anotations.PasswordMatch;
import org.softuni.pathfinder.validation.anotations.UniqueEmail;
import org.softuni.pathfinder.validation.anotations.UniqueUsername;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatch
public class UserRegisterDto {

    @NotEmpty(message = "Username is required")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    @UniqueUsername
    private String username;

    @NotEmpty(message = "Full Name is required")
    @Size(min = 5, max = 20, message = "Full Name must be between 5 and 20 characters")
    private String fullName;

    @NotEmpty(message = "Email is required")
    @Email(regexp = ".+[@].+", message = "{user.email}")
    @UniqueEmail
    private String email;

    @NotNull(message = "Age is required")
    private Integer age;

    @NotEmpty(message = "Password is required")
    @Size(min = 2, message = "{user.confirm-password.length}")
    private String password;

    @NotEmpty(message = "Confirm Password is required")
    @Size(min = 2, message = "{user.confirm-password.length}")
    private String confirmPassword;
}
