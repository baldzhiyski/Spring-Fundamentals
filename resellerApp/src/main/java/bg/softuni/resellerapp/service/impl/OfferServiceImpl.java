package bg.softuni.resellerapp.service.impl;

import bg.softuni.resellerapp.model.Condition;
import bg.softuni.resellerapp.model.Offer;
import bg.softuni.resellerapp.model.User;
import bg.softuni.resellerapp.model.dtos.offer.*;
import bg.softuni.resellerapp.repository.ConditionRepository;
import bg.softuni.resellerapp.repository.OfferRepository;
import bg.softuni.resellerapp.repository.UserRepository;
import bg.softuni.resellerapp.service.OfferService;
import bg.softuni.resellerapp.util.CurrentLoggedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private CurrentLoggedUser currentLoggedUser;
    private UserRepository userRepository;
    private OfferRepository offerRepository;

    private ConditionRepository conditionRepository;



    public OfferServiceImpl(CurrentLoggedUser currentLoggedUser, UserRepository userRepository, OfferRepository offerRepository, ConditionRepository conditionRepository) {
        this.currentLoggedUser = currentLoggedUser;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.conditionRepository = conditionRepository;
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

    @Override
    @Transactional
    public void registerOffer(AddOfferDto offerDto) {
        Offer offer = new Offer();
        String username = currentLoggedUser.getUsername();
        offer.setCreator(this.userRepository.findByUsername(username).get());
        offer.setDescription(offerDto.getDescription());

        Condition condition =this.conditionRepository.getByConditionName(offerDto.getCondition());

        offer.setCondition(condition);
        offer.setPrice(offerDto.getPrice());

        this.offerRepository.saveAndFlush(offer);
    }

    @Override
    public void buy(UUID offerId) {
        Offer offer = this.offerRepository.findById(offerId).orElseThrow();
        offer.setBuyer(this.userRepository.findByUsername(this.currentLoggedUser.getUsername()).orElseThrow());
        this.offerRepository.saveAndFlush(offer);
    }

    @Override
    @Transactional
    public void remove(UUID offerId) {
        this.offerRepository.deleteById(offerId);
    }
}
