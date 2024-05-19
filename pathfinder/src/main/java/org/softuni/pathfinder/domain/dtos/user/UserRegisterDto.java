package org.softuni.pathfinder.domain.dtos.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {

    @NotEmpty(message = "Username is required")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String username;

    @NotEmpty(message = "Full Name is required")
    @Size(min = 5, max = 20, message = "Full Name must be between 5 and 20 characters")
    private String fullName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Must be a valid email")
    private String email;

    @NotNull(message = "Age is required")
    private Integer age;

    @NotEmpty(message = "Password is required")
    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters")
    private String password;

    @NotEmpty(message = "Confirm Password is required")
    @Size(min = 5, max = 20, message = "Confirm Password must be between 5 and 20 characters")
    private String confirmPassword;
}
