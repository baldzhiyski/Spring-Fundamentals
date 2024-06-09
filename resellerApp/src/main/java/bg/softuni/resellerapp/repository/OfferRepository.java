package bg.softuni.resellerapp.repository;

import bg.softuni.resellerapp.model.Offer;
import bg.softuni.resellerapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
    @Query("SELECT o FROM Offer o WHERE o.creator = :user AND o.buyer IS NULL")
    List<Offer> findByCreatorAndNoBuyer(User user);

    List<Offer> findByBuyer(User buyer);

    @Query("SELECT o FROM Offer o WHERE o.creator != :user AND o.buyer != :user")
    List<Offer> findOtherOffers(User user);
}
