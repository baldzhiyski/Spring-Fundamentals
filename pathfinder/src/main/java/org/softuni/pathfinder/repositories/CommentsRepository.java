package org.softuni.pathfinder.repositories;

import org.softuni.pathfinder.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comment,Long> {
}
