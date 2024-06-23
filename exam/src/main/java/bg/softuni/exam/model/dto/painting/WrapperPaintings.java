package bg.softuni.exam.model.dto.painting;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class WrapperPaintings {
    private Set<PaintingDto> createdPaintings;
    private Set<FavouritePaintingDto> favouritePaintings;
    private Set<OtherPaintingDto> otherPaintings;
    private Set<PaintingDto> mostRated;

    public WrapperPaintings() {
        this.createdPaintings = new HashSet<>();
        this.favouritePaintings = new HashSet<>();
        this.otherPaintings = new HashSet<>();
        this.mostRated = new HashSet<>();
    }
}
