package bg.softuni.dictionaryapp.service.impl;

import bg.softuni.dictionaryapp.model.Language;
import bg.softuni.dictionaryapp.model.enums.LanguageName;
import bg.softuni.dictionaryapp.repo.LanguageRepository;
import bg.softuni.dictionaryapp.service.SeedService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeedServiceImpl implements SeedService {
    private LanguageRepository languageRepository;

    public SeedServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public void seedLanguagesIntoDB() {
        if(this.languageRepository.count()==0) {
            List<Language> languages = Arrays.stream(LanguageName.values())
                    .map(Language::new)
                    .collect(Collectors.toList());

            this.languageRepository.saveAllAndFlush(languages);
        }
    }
}
