package bg.softuni.exam.repository;


import bg.softuni.exam.model.entity.Painting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaintingRepository  extends JpaRepository<Painting, UUID> {
}
