package org.softuni.mobilele;

import org.softuni.mobilele.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private SeedService service;

    @Autowired
    public DataInitializer(SeedService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        this.service.seedAll();
    }
}
