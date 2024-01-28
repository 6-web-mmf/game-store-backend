package com.elyashevich.store.controller;

import com.elyashevich.store.dto.orderDto.OrderCreateDto;
import com.elyashevich.store.entity.Order;
import com.elyashevich.store.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="order_controller")
@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "https://6-web-mmf.github.io")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(final @RequestBody @Valid OrderCreateDto orderCreateDto) {
        return orderService.create(orderCreateDto);
    }

    @GetMapping("/{id}")
    public Order findById(final @PathVariable String id) {
        return orderService.findById(id);
    }

    @PatchMapping("/{id}")
    public Order update(final @PathVariable String id, final @RequestBody @Valid OrderCreateDto orderCreateDto) {
        return orderService.purchase(, );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(final @PathVariable String id) {
        orderService.delete(id);
    }
}
