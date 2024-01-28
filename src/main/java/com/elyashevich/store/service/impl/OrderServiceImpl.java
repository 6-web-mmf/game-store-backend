package com.elyashevich.store.service.impl;

import com.elyashevich.store.dto.orderDto.OrderCreateDto;
import com.elyashevich.store.dto.userDto.UserUpdateDto;
import com.elyashevich.store.entity.Game;
import com.elyashevich.store.entity.Order;
import com.elyashevich.store.entity.User;
import com.elyashevich.store.exception.BadRequestException;
import com.elyashevich.store.exception.NotFoundException;
import com.elyashevich.store.mapper.OrderMapper;
import com.elyashevich.store.repository.OrderRepository;
import com.elyashevich.store.service.GameService;
import com.elyashevich.store.service.OrderService;
import com.elyashevich.store.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final GameService gameService;
    private final OrderMapper orderMapper;

    @Override
    public Order create(OrderCreateDto orderCreateDto) {
        final User user = userService.findById(orderCreateDto.userId());
        final Game game = gameService.findById(orderCreateDto.gameId());
        final Order order = orderMapper.convert(orderCreateDto);
        log.info("CREATE NEW ORDER\n" + user);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAll() {
        log.info("FIND ALL ORDERS");
        return orderRepository.findAll();
    }

    @Override
    public Order findById(String id) {
        log.info("FIND ORDER BY ID");
        return orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Order with id = %s wasn't found", id))
        );
    }

    @Override
    public void purchase(String gameId, String userId, String orderId) {
        final User user = userService.findById(userId);
        final Game game = gameService.findById(gameId);
        if (user.getBalance() < game.getPrice()) {
            throw new BadRequestException("PRICE > BALANCE");
        }
        user.setBalance(user.getBalance() - game.getPrice());
        userService.update(
                userId,
                new UserUpdateDto(user.getUsername(), user.getEmail(), user.getPassword(), user.getBalance())
        );
        delete(orderId);
        gameService.delete(gameId);
    }

    @Override
    public void delete(String id) {
        final Order order = orderRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Order with id = %s wasn't found", id))
        );
        log.info("DELETE ORDER\n" + order);
        orderRepository.delete(order);
    }
}
