package bg.softuni.resellerapp.model;

import bg.softuni.resellerapp.model.enums.ConditionName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
@Table(name = "conditions")
public class Condition extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private ConditionName conditionName;

    @Column
    @NotBlank
    private String description;


    public Condition() {
    }


    public ConditionName getConditionName() {
        return conditionName;
    }

    public void setConditionName(ConditionName conditionName) {
        this.conditionName = conditionName;
        setDescription(conditionName);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(ConditionName conditionName) {
        String description;
        switch (conditionName){
            case GOOD -> description = "Some signs of wear and tear or minor defects";
            case EXCELLENT -> description="In perfect condition";
            case ACCEPTABLE -> description = "The item is fairly worn but continues to function properly";
            default -> throw new IllegalArgumentException("Invalid condition name");
        }
        this.description =description;
    }
}





