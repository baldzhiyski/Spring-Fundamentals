package bg.softuni.exam.model.dto.painting;

import bg.softuni.exam.model.entity.Painting;
import bg.softuni.exam.model.entity.enums.StyleName;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OtherPaintingDto {
    private UUID id;
    private String name;

    private String author;

    private StyleName style;
    private String ownerUsername;
    private String imageUrl;


    public static OtherPaintingDto fromPaintingEntity(Painting painting){
        OtherPaintingDto otherPaintingDto = new OtherPaintingDto();

        otherPaintingDto.setId(painting.getId());
        otherPaintingDto.setName(painting.getName());
        otherPaintingDto.setAuthor(painting.getAuthor());
        otherPaintingDto.setStyle(painting.getStyle().getStyleName());
        otherPaintingDto.setOwnerUsername(painting.getOwner().getUsername());
        otherPaintingDto.setImageUrl(painting.getImageUrl());
        return otherPaintingDto;
    }
}
