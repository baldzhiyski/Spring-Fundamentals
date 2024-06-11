package bg.softuni.resellerapp.model.dtos.offer;

import bg.softuni.resellerapp.model.Offer;

public class BoughtOfferDto {

    private String itemDescritpion;
    private Double itemPrice;

    public BoughtOfferDto(Offer offer) {
        setItemDescritpion(offer.getDescription());
        setItemPrice(offer.getPrice());
    }

    public String getItemDescritpion() {
        return itemDescritpion;
    }

    public BoughtOfferDto setItemDescritpion(String itemDescritpion) {
        this.itemDescritpion = itemDescritpion;
        return this;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public BoughtOfferDto setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
        return this;
    }
}
