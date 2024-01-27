package com.elyashevich.store.controller;

import com.elyashevich.store.dto.authDto.SignUpDto;
import com.elyashevich.store.dto.userDto.UserUpdateDto;
import com.elyashevich.store.entity.User;
import com.elyashevich.store.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Tag(name="user_controller")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> findAll(final @RequestParam String q) {
        return userService.findAll(q);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(
            final @RequestBody @Valid SignUpDto signUpDto
            //final @RequestBody @Valid ImageCreateDto imageCreateDto
    ) throws IOException {
        return userService.create(signUpDto/*, imageCreateDto*/);
    }

    @GetMapping("/{id}")
    public User findById(final @PathVariable String id){
        return userService.findById(id);
    }

    @PatchMapping("/{id}")
    public User update(final @RequestBody @Valid UserUpdateDto userUpdateDto, final @PathVariable String id) {
        return userService.update(id, userUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(final @PathVariable String id) {
        userService.delete(id);
    }

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public User createAdmin(final @RequestBody @Valid SignUpDto signUpDto) {
        return userService.createAdmin(signUpDto);
    }
}
