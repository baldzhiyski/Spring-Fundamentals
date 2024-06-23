package bg.softuni.exam.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.UUID;

@Component
@SessionScope
@Getter
@Setter
public class CurrentLoggedUser {

    private UUID id;

    private String username;

    private boolean isLogged;

}
