package bg.softuni.dictionaryapp.repo;

import bg.softuni.dictionaryapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,UUID> {
    Optional<User> getByUsername(String username);

    Optional<User> getByEmail(String email);
}
