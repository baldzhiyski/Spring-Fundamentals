package bg.softuni.resellerapp.service.impl;

import bg.softuni.resellerapp.model.Offer;
import bg.softuni.resellerapp.model.User;
import bg.softuni.resellerapp.model.dtos.BoughtOfferDto;
import bg.softuni.resellerapp.model.dtos.LoggedUserOffer;
import bg.softuni.resellerapp.model.dtos.LoggedUserOffersDto;
import bg.softuni.resellerapp.model.dtos.OtherOfferDto;
import bg.softuni.resellerapp.repository.OfferRepository;
import bg.softuni.resellerapp.repository.UserRepository;
import bg.softuni.resellerapp.service.OfferService;
import bg.softuni.resellerapp.util.CurrentLoggedUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private CurrentLoggedUser currentLoggedUser;
    private UserRepository userRepository;
    private OfferRepository offerRepository;

    public OfferServiceImpl(CurrentLoggedUser currentLoggedUser, UserRepository userRepository, OfferRepository offerRepository) {
        this.currentLoggedUser = currentLoggedUser;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    public LoggedUserOffersDto getInfoLoggedUser() {
        User user = this.userRepository.findByUsername(currentLoggedUser.getUsername()).orElseThrow();

        List<Offer> boughtOffers = this.offerRepository.findByBuyer(user);
        List<Offer> createdOffers = this.offerRepository.findByCreatorAndNoBuyer(user);
        List<Offer> otherOffers = this.offerRepository.findOtherOffers(user);

        LoggedUserOffersDto loggedUserOffersDto = new LoggedUserOffersDto();

        Set<BoughtOfferDto> boughtOffersDto = boughtOffers.stream().map(BoughtOfferDto::new).collect(Collectors.toSet());
        Set<LoggedUserOffer> myOffers = createdOffers.stream().map(LoggedUserOffer::new).collect(Collectors.toSet());
        Set<OtherOfferDto> otherOfferDtos= otherOffers.stream().map(OtherOfferDto::new).collect(Collectors.toSet());

        loggedUserOffersDto.setBoughtOffers(boughtOffersDto);
        loggedUserOffersDto.setOtherOffers(otherOfferDtos);
        loggedUserOffersDto.setLoggedUserOffers(myOffers);
        loggedUserOffersDto.setUsername(user.getUsername());

        return loggedUserOffersDto;
    }
}
