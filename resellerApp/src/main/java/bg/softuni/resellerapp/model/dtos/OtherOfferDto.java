package bg.softuni.resellerapp.model.dtos;

import bg.softuni.resellerapp.model.Condition;
import bg.softuni.resellerapp.model.Offer;

import java.util.UUID;

public class OtherOfferDto {
    private String offerDescription;

    private UUID id;

    private Condition condition;

    private Double price;

    private String sellerUsername;

    public OtherOfferDto(Offer offer){
        setId(offer.getId());
        setOfferDescription(offer.getDescription());
        setCondition(offer.getCondition());
        setSellerUsername(offer.getCreator().getUsername());
        setPrice(offer.getPrice());
    }

    public UUID getId() {
        return id;
    }

    public OtherOfferDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public OtherOfferDto setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
        return this;
    }
    public String getOfferDescription() {
        return offerDescription;
    }

    public OtherOfferDto setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
        return this;
    }

    public Condition getCondition() {
        return condition;
    }

    public OtherOfferDto setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public OtherOfferDto setPrice(Double price) {
        this.price = price;
        return this;
    }
}
