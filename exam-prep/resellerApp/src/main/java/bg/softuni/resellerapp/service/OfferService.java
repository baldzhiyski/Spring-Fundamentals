package bg.softuni.resellerapp.service;

import bg.softuni.resellerapp.model.dtos.offer.AddOfferDto;
import bg.softuni.resellerapp.model.dtos.offer.LoggedUserOffersDto;
import bg.softuni.resellerapp.util.CurrentLoggedUser;

import java.util.UUID;

public interface OfferService {

    LoggedUserOffersDto getInfoLoggedUser();

    void registerOffer(AddOfferDto offerDto);

    void buy(UUID offerId);

    void remove(UUID offerId);
}
