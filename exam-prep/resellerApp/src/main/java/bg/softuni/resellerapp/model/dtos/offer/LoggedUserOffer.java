package bg.softuni.resellerapp.model.dtos.offer;

import bg.softuni.resellerapp.model.Condition;
import bg.softuni.resellerapp.model.Offer;

import java.util.UUID;

public class LoggedUserOffer {

    private UUID id;
    private Condition condition;
    private Double price;

    private String description;

    public LoggedUserOffer(Offer offer){
        setId(offer.getId());
        setCondition(offer.getCondition());
        setPrice(offer.getPrice());
        setDescription(offer.getDescription());
    }
    public Condition getCondition() {
        return condition;
    }

    public UUID getId() {
        return id;
    }

    public LoggedUserOffer setId(UUID id) {
        this.id = id;
        return this;
    }

    public LoggedUserOffer setCondition(Condition condition) {
        this.condition = condition;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public LoggedUserOffer setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LoggedUserOffer setDescription(String description) {
        this.description = description;
        return this;
    }
}
