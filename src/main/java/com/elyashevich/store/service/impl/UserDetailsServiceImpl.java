package com.elyashevich.store.service.impl;

import com.elyashevich.store.dto.authDto.SignUpDto;
import com.elyashevich.store.entity.Role;
import com.elyashevich.store.entity.User;
import com.elyashevich.store.exception.NotFoundException;
import com.elyashevich.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException(String.format("User with username = %s wasn't found!", username))
        );
        Collection<GrantedAuthority> roles = user.getRoles()
                .stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                roles
        );
    }

    public User createNewUser(SignUpDto registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.username());
        user.setEmail(registrationUserDto.email());
        user.setPassword(passwordEncoder.encode(registrationUserDto.password()));
        final List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);
        log.info("CREATE NEW USER\n" + user);
        return userRepository.save(user);
    }
}
