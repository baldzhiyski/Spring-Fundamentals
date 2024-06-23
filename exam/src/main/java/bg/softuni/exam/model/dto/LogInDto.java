package bg.softuni.exam.model.dto;

import bg.softuni.exam.vallidation.annotations.CorrectUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CorrectUser
public class LogInDto {

    private String username;
    private String password;
}
