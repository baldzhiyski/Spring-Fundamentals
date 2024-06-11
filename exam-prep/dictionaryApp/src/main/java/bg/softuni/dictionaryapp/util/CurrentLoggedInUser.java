package bg.softuni.dictionaryapp.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.UUID;

@Component
@SessionScope
public class CurrentLoggedInUser {
    private UUID id;
    private String username;
    private boolean isLogged;

    public UUID getId() {
        return id;
    }

    public CurrentLoggedInUser setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public CurrentLoggedInUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public CurrentLoggedInUser setLogged(boolean logged) {
        isLogged = logged;
        return this;
    }
}
