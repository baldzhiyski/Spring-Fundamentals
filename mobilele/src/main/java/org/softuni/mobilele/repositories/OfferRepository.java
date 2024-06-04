package org.softuni.mobilele.repositories;

import org.softuni.mobilele.domain.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

    @Modifying
    @Query("DELETE FROM Offer o WHERE o.id = :offerId")
    void deleteOffersByModelId(Long offerId);

    long countByModelId(Long id);
}
