package org.softuni.mobilele;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class MobileleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileleApplication.class, args);
    }

}
