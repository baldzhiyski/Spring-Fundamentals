package org.softuni.mobilele.services;

import org.softuni.mobilele.domain.entities.Offer;

import java.util.List;

public interface OfferService {

    List<Offer> getAllOffers();

    void addOffer();
}
