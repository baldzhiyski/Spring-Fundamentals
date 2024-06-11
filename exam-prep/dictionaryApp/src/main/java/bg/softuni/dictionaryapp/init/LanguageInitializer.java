package bg.softuni.dictionaryapp.init;

import bg.softuni.dictionaryapp.service.SeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LanguageInitializer implements CommandLineRunner {
    private SeedService seedService;

    public LanguageInitializer(SeedService service) {
        this.seedService = service;
    }

    @Override
    public void run(String... args) throws Exception {
        seedService.seedLanguagesIntoDB();
    }
}
