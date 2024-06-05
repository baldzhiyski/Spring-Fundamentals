package org.softuni.pathfinder.services.impl;

import org.softuni.pathfinder.domain.entities.Picture;
import org.softuni.pathfinder.repositories.PictureRepository;
import org.softuni.pathfinder.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl  implements PictureService {
    private PictureRepository pictureRepository;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Picture getRandomPicture(Long routeId) {
        return this.pictureRepository.getRandomPicture(routeId);
    }
}
