package bg.softuni.exam.repository;

import bg.softuni.exam.model.entity.Style;
import bg.softuni.exam.model.entity.enums.StyleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StyleRepository extends JpaRepository<Style, UUID> {

    Style findByStyleName(StyleName styleName);
}
