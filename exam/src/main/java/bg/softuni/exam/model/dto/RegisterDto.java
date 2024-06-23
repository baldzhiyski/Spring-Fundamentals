package bg.softuni.exam.model.dto;

import bg.softuni.exam.vallidation.annotations.PasswordMatch;
import bg.softuni.exam.vallidation.annotations.UniqueEmail;
import bg.softuni.exam.vallidation.annotations.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@PasswordMatch
public class RegisterDto {

    @UniqueUsername
    @NotNull
    private String username;

    @UniqueEmail
    @Email
    private String email;

    @Length(min = 3,max = 20)
    private String password;
    @Length(min = 3,max = 20)
    private String confirmPassword;

}
