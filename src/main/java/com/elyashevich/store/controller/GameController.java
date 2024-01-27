package com.elyashevich.store.controller;

import com.elyashevich.store.dto.gameDto.GameCreateDto;
import com.elyashevich.store.dto.gameDto.GameUpdateDto;
import com.elyashevich.store.entity.Game;
import com.elyashevich.store.service.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "game_controller")
@RestController
@RequestMapping("/api/v1/games")
@CrossOrigin(origins = "http://127.0.0.1:5173/")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public List<Game> findAll(final @RequestParam String q) {
        return gameService.findAll(q);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game create(
            final @RequestBody @Valid GameCreateDto gameCreateDto
    ) throws IOException {
        return gameService.create(gameCreateDto);
    }

    @GetMapping("/{id}")
    public Game findById(final @PathVariable String id) {
        return gameService.findById(id);
    }

    @PatchMapping("/{id}")
    public Game update(final @PathVariable String id, final @RequestBody @Valid GameUpdateDto gameUpdateDto) {
        return gameService.update(id, gameUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(final @PathVariable String id) {
        gameService.delete(id);
    }
}
