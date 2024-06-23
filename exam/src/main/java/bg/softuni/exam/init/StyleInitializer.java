package bg.softuni.exam.init;

import bg.softuni.exam.repository.StyleRepository;
import bg.softuni.exam.service.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StyleInitializer implements CommandLineRunner {

    private SeedService seedService;

    public StyleInitializer(SeedService seedService) {
        this.seedService = seedService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedService.seedStyle();
    }
}
