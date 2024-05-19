package org.softuni.pathfinder.services;

import org.softuni.pathfinder.domain.dtos.comments.CommentDto;

public interface CommentService {
    void registerComment(CommentDto commentDto);
}
