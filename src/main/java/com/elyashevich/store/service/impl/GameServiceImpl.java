package com.elyashevich.store.service.impl;

import com.elyashevich.store.dto.gameDto.GameCreateDto;
import com.elyashevich.store.dto.gameDto.GameUpdateDto;
import com.elyashevich.store.entity.Game;
import com.elyashevich.store.exception.NotFoundException;
import com.elyashevich.store.mapper.GameMapper;
import com.elyashevich.store.repository.GameRepository;
import com.elyashevich.store.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public Game create(GameCreateDto gameCreateDto) {
        final Game game = gameMapper.convert(gameCreateDto);
        return gameRepository.save(game);
    }

    @Override
    public Game findById(String id) {
        return gameRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Game with id = %s wasn't found", id)));
    }

    @Override
    public Game update(String id, GameUpdateDto gameUpdateDto) {
        final Game game = findById(id);
        game.setTitle(gameUpdateDto.title());
        game.setDescription(gameUpdateDto.description());
        game.setPrice(gameUpdateDto.price());
        game.setViews(gameUpdateDto.views());
        game.setRating(gameUpdateDto.rating());
        return gameRepository.save(game);
    }

    @Override
    public List<Game> findAll(String q) {
        if (!q.isEmpty()) {
            return gameRepository.findByQueryTitle(q.toLowerCase());
        }
        return gameRepository.findAll();
    }

    @Override
    public void delete(String id) {
        final Game game = findById(id);
        gameRepository.delete(game);
    }
}
