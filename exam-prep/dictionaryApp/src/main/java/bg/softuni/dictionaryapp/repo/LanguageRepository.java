package bg.softuni.dictionaryapp.repo;

import bg.softuni.dictionaryapp.model.Language;
import bg.softuni.dictionaryapp.model.enums.LanguageName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {
    Language findByName(LanguageName languageName);
}
