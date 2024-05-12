package org.softuni.mobilele.repositories;

import org.softuni.mobilele.domain.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {

    Optional<UserRole> findById(Long id);
}
