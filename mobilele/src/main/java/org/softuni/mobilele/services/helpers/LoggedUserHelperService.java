package org.softuni.mobilele.services.helpers;

import org.softuni.mobilele.domain.entities.Offer;
import org.softuni.mobilele.repositories.OfferRepository;
import org.softuni.mobilele.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO
@Service
public class LoggedUserHelperService {
    private CurrentUser currentUser;
    private OfferRepository offerRepository;

    @Autowired
    public LoggedUserHelperService(CurrentUser currentUser, OfferRepository offerRepositoryl) {
        this.currentUser = currentUser;
        this.offerRepository = offerRepositoryl;
    }

    public boolean isLoggedCreator(Long offerId) {
        Offer offerById = offerRepository.findById(offerId).orElseThrow();

        return offerById.getSeller().getId().equals(this.currentUser.getId());
    }

    public CurrentUser getLoggedUser() {
        return currentUser;
    }
}
