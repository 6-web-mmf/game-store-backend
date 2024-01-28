package com.elyashevich.store.service.impl;

import com.elyashevich.store.dto.authDto.LoginDto;
import com.elyashevich.store.dto.authDto.LoginResponseDto;
import com.elyashevich.store.dto.authDto.SignUpDto;
import com.elyashevich.store.entity.User;
import com.elyashevich.store.security.JwtToken;
import com.elyashevich.store.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsServiceImpl userService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDto login(LoginDto authRequest) {
        authenticationManager.authenticate
                (
                        new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
                );
        UserDetails userDetails = userService.loadUserByUsername(authRequest.username());
        String token = JwtToken.generateToken(userDetails);
        log.info("SIGN IN");
        return LoginResponseDto
                .builder()
                .token(token)
                .build();
    }

    public LoginResponseDto signUp(SignUpDto registrationUserDto) {
        User user = userService.createNewUser(registrationUserDto);
        UserDetails userDetails = userService.loadUserByUsername(registrationUserDto.username());
        String token = JwtToken.generateToken(userDetails);
        log.info("SIGN UP");
        return LoginResponseDto.builder()
                .token(token)
                .build();
    }

}
