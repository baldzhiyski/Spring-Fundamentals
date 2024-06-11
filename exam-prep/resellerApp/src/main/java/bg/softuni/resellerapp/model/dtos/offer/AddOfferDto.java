package bg.softuni.resellerapp.model.dtos.offer;

import bg.softuni.resellerapp.model.Condition;
import bg.softuni.resellerapp.model.enums.ConditionName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public class AddOfferDto {

    @Length(min = 10,message = "Please add description with length minimum 10 symbols !")
    private String description;

    @Positive(message = "Price should be positive number !")
    @NotNull(message = "Price is required !")
    private Double price;

    @NotNull(message = "Condition is required !")
    private ConditionName condition;



    public AddOfferDto setDescription(String description) {
        this.description = description;
        return this;
    }



    public AddOfferDto setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public ConditionName getCondition() {
        return condition;
    }

    public AddOfferDto setCondition(ConditionName condition) {
        this.condition = condition;
        return this;
    }

}
