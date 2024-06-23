package bg.softuni.exam.model.dto.painting;

import bg.softuni.exam.model.entity.Painting;
import bg.softuni.exam.model.entity.Style;
import bg.softuni.exam.model.entity.enums.StyleName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PaintingDto {
    private UUID id;
    private String name;

    private String author;

    private StyleName style;

    private Integer votes;

    boolean isFavouriteByOthers;

    private String imageUrl;

    public static PaintingDto fromPainting(Painting painting) {
        PaintingDto paintingDto = new PaintingDto();
        paintingDto.setId(painting.getId());
        paintingDto.setName(painting.getName()); // Set the name from the Painting entity
        paintingDto.setAuthor(painting.getAuthor()); // Set the author from the Painting entity
        paintingDto.setStyle(painting.getStyle().getStyleName()); // Assuming painting.getStyle() returns a Style entity
        paintingDto.setVotes(painting.getVotes());
        paintingDto.setFavouriteByOthers(painting.isFavourite());
        paintingDto.setImageUrl(painting.getImageUrl());
        return paintingDto;
    }
}
