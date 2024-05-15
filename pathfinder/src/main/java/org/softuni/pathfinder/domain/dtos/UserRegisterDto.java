package org.softuni.pathfinder.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull
    private String username;

    private String fullName;

    @Email
    private String email;

    @Positive
    private Integer age;

    @Length(min = 2,max = 20)
    private String password;

    @Length(min = 2,max = 20)
    private String confirmPassword;
}
