package bg.softuni.exam.service.impl;

import bg.softuni.exam.model.dto.painting.*;
import bg.softuni.exam.model.entity.Painting;
import bg.softuni.exam.model.entity.Style;
import bg.softuni.exam.model.entity.User;
import bg.softuni.exam.repository.PaintingRepository;
import bg.softuni.exam.repository.StyleRepository;
import bg.softuni.exam.repository.UserRepository;
import bg.softuni.exam.service.PaintingService;
import bg.softuni.exam.util.CurrentLoggedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PaintingServiceImpl implements PaintingService {
    private PaintingRepository paintingRepository;
    private CurrentLoggedUser currentLoggedUser;
    private UserRepository userRepository;
    private StyleRepository styleRepository;

    public PaintingServiceImpl(PaintingRepository paintingRepository, CurrentLoggedUser currentLoggedUser, UserRepository userRepository, StyleRepository styleRepository) {
        this.paintingRepository = paintingRepository;
        this.currentLoggedUser = currentLoggedUser;
        this.userRepository = userRepository;
        this.styleRepository = styleRepository;
    }

    @Override
    public Set<PaintingDto> getAllByCurrentUser() {
        User user = this.userRepository.findByUsername(currentLoggedUser.getUsername()).orElseThrow();
        return user.getCreatedPaintings() .stream().map(PaintingDto::fromPainting).collect(Collectors.toSet());
    }

    @Override
    public Set<OtherPaintingDto> getAllByOtherUsers() {
        return this.userRepository.findAllByOwnerNotAndNotInFavouritePaintings(userRepository.findByUsername(currentLoggedUser.getUsername()).orElseThrow())
                .stream().map(OtherPaintingDto::fromPaintingEntity).collect(Collectors.toSet());
    }

    @Override
    public Set<FavouritePaintingDto> getFavourites() {
        User user = this.userRepository.findByUsername(currentLoggedUser.getUsername()).orElseThrow();
        return user.getFavouritePaintings().stream().map(FavouritePaintingDto::fromPainting).collect(Collectors.toSet());
    }

    @Override
    public Set<PaintingDto> getMostRated() {
        return this.paintingRepository.findTop2ByOrderByVotesDescAndName().stream().map(PaintingDto::fromPainting).collect(Collectors.toSet());
    }


    @Override
    public WrapperPaintings getWrapper() {
        WrapperPaintings wrapperPaintings = new WrapperPaintings();

        wrapperPaintings.setCreatedPaintings(getAllByCurrentUser());
        wrapperPaintings.setFavouritePaintings(getFavourites());
        wrapperPaintings.setOtherPaintings(getAllByOtherUsers());
        Set<PaintingDto> mostRated = getMostRated();
        wrapperPaintings.setMostRated(mostRated);
        return wrapperPaintings;
    }

    @Override
    @Transactional
    public void addPainting(AddPaintingDto addPaintingDto) {
        Painting painting = new Painting();
        painting.setName(addPaintingDto.getName());
        Style byStyleName = styleRepository.findByStyleName(addPaintingDto.getStyleName());
        painting.setFavourite(false);
        painting.setStyle(byStyleName);
        painting.setAuthor(addPaintingDto.getAuthor());
        painting.setOwner(this.userRepository.findByUsername(this.currentLoggedUser.getUsername()).orElseThrow());
        painting.setVotes(0);

        this.paintingRepository.saveAndFlush(painting);
    }


}
