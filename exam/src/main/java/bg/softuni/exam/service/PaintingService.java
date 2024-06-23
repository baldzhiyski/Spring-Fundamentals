package bg.softuni.exam.service;

import bg.softuni.exam.model.dto.painting.*;
import bg.softuni.exam.util.CurrentLoggedUser;

import java.util.Set;
import java.util.UUID;

public interface PaintingService {

    Set<PaintingDto> getAllByCurrentUser();
    Set<OtherPaintingDto> getAllByOtherUsers();
    Set<FavouritePaintingDto> getFavourites();
    Set<PaintingDto> getMostRated();
    WrapperPaintings getWrapper();

    void addPainting(AddPaintingDto addPaintingDto);
}
