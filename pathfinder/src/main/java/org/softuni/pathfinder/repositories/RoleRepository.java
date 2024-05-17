package org.softuni.pathfinder.repositories;

import org.softuni.pathfinder.domain.entities.Picture;
import org.softuni.pathfinder.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository  extends JpaRepository<Role,Long> {

    Role getById(Long id);
}
