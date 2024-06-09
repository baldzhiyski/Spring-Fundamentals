package bg.softuni.resellerapp.model.dtos.offer;

import java.util.Set;

public class LoggedUserOffersDto {
    private String username;

    private Set<BoughtOfferDto> boughtOffers;

    private Set<OtherOfferDto> otherOffers;

    private Set<LoggedUserOffer> loggedUserOffers;

    public LoggedUserOffersDto setBoughtOffers(Set<BoughtOfferDto> boughtOffers) {
        this.boughtOffers = boughtOffers;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LoggedUserOffersDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public LoggedUserOffersDto setOtherOffers(Set<OtherOfferDto> otherOffers) {
        this.otherOffers = otherOffers;
        return this;
    }

    public LoggedUserOffersDto setLoggedUserOffers(Set<LoggedUserOffer> loggedUserOffers) {
        this.loggedUserOffers = loggedUserOffers;
        return this;
    }

    public Set<BoughtOfferDto> getBoughtOffers() {
        return boughtOffers;
    }

    public Set<OtherOfferDto> getOtherOffers() {
        return otherOffers;
    }

    public Set<LoggedUserOffer> getLoggedUserOffers() {
        return loggedUserOffers;
    }
}
