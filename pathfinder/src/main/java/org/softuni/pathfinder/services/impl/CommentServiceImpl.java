package org.softuni.pathfinder.services.impl;

import org.modelmapper.ModelMapper;
import org.softuni.pathfinder.domain.dtos.comments.CommentDto;
import org.softuni.pathfinder.domain.entities.Comment;
import org.softuni.pathfinder.repositories.CommentsRepository;
import org.softuni.pathfinder.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentsRepository commentsRepository;
    private ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(CommentsRepository commentsRepository, ModelMapper mapper) {
        this.commentsRepository = commentsRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void registerComment(CommentDto commentDto) {
        Comment map = this.mapper.map(commentDto, Comment.class);

        this.commentsRepository.saveAndFlush(map);
    }
}
