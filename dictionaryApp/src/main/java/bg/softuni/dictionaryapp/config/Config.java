package bg.softuni.dictionaryapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Config {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Converter<String, Date> stringToDateConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                if (source == null || source.isEmpty()) {
                    return null;
                }
                try {
                    return new SimpleDateFormat("yyyy-MM-dd").parse(source);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd");
                }
            }
        };
    }
}
