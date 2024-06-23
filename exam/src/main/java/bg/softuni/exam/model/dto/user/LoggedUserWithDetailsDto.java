package bg.softuni.exam.model.dto.user;

import bg.softuni.exam.model.dto.painting.FavouritePaintingDto;
import bg.softuni.exam.model.dto.painting.OtherPaintingDto;
import bg.softuni.exam.model.dto.painting.PaintingDto;
import bg.softuni.exam.model.dto.painting.WrapperPaintings;
import bg.softuni.exam.model.entity.Painting;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class LoggedUserWithDetailsDto {

    private String username;

    private WrapperPaintings wrapperPaintings;

    public LoggedUserWithDetailsDto(){
        this.wrapperPaintings = new WrapperPaintings();
    }
}
