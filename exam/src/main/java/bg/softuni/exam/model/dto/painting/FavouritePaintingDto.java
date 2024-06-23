package bg.softuni.exam.model.dto.painting;

import bg.softuni.exam.model.entity.Painting;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FavouritePaintingDto {
    private UUID id;
    private String name;

    private String author;

    private String ownerUsername;
    private String imageUrl;


    public static FavouritePaintingDto fromPainting(Painting painting){
        FavouritePaintingDto favouritePaintingDto = new FavouritePaintingDto();

        favouritePaintingDto.setId(painting.getId());
        favouritePaintingDto.setName(painting.getName());
        favouritePaintingDto.setAuthor(painting.getAuthor());
        favouritePaintingDto.setOwnerUsername(painting.getOwner().getUsername());
        favouritePaintingDto.setImageUrl(painting.getImageUrl());
        return favouritePaintingDto;
    }
}

