package bg.softuni.resellerapp.repository;

import bg.softuni.resellerapp.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
}
