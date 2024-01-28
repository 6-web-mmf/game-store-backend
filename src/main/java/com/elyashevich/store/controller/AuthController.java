package com.elyashevich.store.controller;

import com.elyashevich.store.dto.authDto.LoginDto;
import com.elyashevich.store.dto.authDto.LoginResponseDto;
import com.elyashevich.store.dto.authDto.SignUpDto;
import com.elyashevich.store.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="auth_controller")
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "https://6-web-mmf.github.io")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LoginResponseDto login(final @RequestBody @Valid LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponseDto signUp(final @RequestBody @Valid SignUpDto signUpDto) {
        return authService.signUp(signUpDto);
    }

}
