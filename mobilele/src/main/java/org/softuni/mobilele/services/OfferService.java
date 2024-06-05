package org.softuni.mobilele.services;

import org.softuni.mobilele.domain.dtos.offer.*;
import org.softuni.mobilele.domain.entities.Offer;

import java.io.IOException;
import java.util.List;

public interface OfferService {

    List<OfferDisplayDto> getAllOffers();

    void addOffer(OfferRegisterDto offerRegisterDto) throws IOException;

    OfferDetailsDto getOfferById(Long id);

    void deleteOffer(Long offerId);

    void updateOffer(Long offerId, OfferUpdateDto offerUpdateDto) throws IOException;
}
