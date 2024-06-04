package org.softuni.mobilele.services;

import org.softuni.mobilele.domain.dtos.offer.OfferDetailsDto;
import org.softuni.mobilele.domain.dtos.offer.OfferDto;
import org.softuni.mobilele.domain.dtos.offer.OfferRegisterDto;
import org.softuni.mobilele.domain.entities.Offer;

import java.io.IOException;
import java.util.List;

public interface OfferService {

    List<Offer> getAllOffers();

    void addOffer(OfferRegisterDto offerRegisterDto) throws IOException;

    OfferDetailsDto getOfferById(Long id);

    void deleteOffer(Long offerId);
}
