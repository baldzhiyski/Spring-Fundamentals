package bg.softuni.resellerapp;

import bg.softuni.resellerapp.service.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
    private SeedService seedService;

    public Runner(SeedService service) {
        this.seedService = service;
    }

    @Override
    public void run(String... args) throws Exception {
        seedService.seedConditions();
    }
}
