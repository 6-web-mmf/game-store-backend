package com.elyashevich.store.controller;

import com.elyashevich.store.dto.commentDto.CommentCreateDto;
import com.elyashevich.store.dto.commentDto.CommentUpdateDto;
import com.elyashevich.store.entity.Comment;
import com.elyashevich.store.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<Comment> findAll() {
        return commentService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(final @RequestBody @Valid CommentCreateDto commentCreateDto) {
        return commentService.create(commentCreateDto);
    }

    @GetMapping("/{id}")
    public Comment findById(final @PathVariable String id) {
        return commentService.findById(id);
    }

    @PatchMapping("/{id}")
    public Comment update(final @PathVariable String id, final @Valid CommentUpdateDto commentUpdateDto) {
        return commentService.update(id, commentUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(final @PathVariable String id) {
        commentService.delete(id);
    }
}
