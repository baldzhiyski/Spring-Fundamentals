package org.softuni.pathfinder.services;

import org.softuni.pathfinder.domain.entities.Picture;

public interface PictureService {

    Picture getRandomPicture(Long routeId);
}
