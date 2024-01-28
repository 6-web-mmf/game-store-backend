package com.elyashevich.store.service.impl;

import com.elyashevich.store.dto.commentDto.CommentCreateDto;
import com.elyashevich.store.dto.commentDto.CommentUpdateDto;
import com.elyashevich.store.entity.Comment;
import com.elyashevich.store.exception.NotFoundException;
import com.elyashevich.store.mapper.CommentMapper;
import com.elyashevich.store.repository.CommentRepository;
import com.elyashevich.store.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Comment create(CommentCreateDto commentCreateDto) {
        final Comment comment = commentMapper.convert(commentCreateDto);
        log.info("CREATE NEW COMMENT\n" + comment);
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(String id) {
        log.info("FIND COMMENT BY ID");
        return commentRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Comment with id = %s wasn't found", id)));
    }

    @Override
    public Comment update(String id, CommentUpdateDto commentUpdateDto) {
        final Comment comment = findById(id);
        comment.setText(commentUpdateDto.text());
        log.info("UPDATE COMMENT\n" + comment);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAll() {
        log.info("FIND ALL COMMENTS");
        return commentRepository.findAll();
    }

    @Override
    public void delete(String id) {
        final Comment comment = findById(id);
        log.info("DELETE COMMENT\n" + comment);
        commentRepository.delete(comment);
    }
}
