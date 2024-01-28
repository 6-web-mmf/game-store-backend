package com.elyashevich.store.service.impl;

import com.elyashevich.store.dto.authDto.SignUpDto;
import com.elyashevich.store.dto.userDto.UserUpdateDto;
import com.elyashevich.store.entity.Role;
import com.elyashevich.store.entity.User;
import com.elyashevich.store.exception.BadRequestException;
import com.elyashevich.store.exception.NotFoundException;
import com.elyashevich.store.mapper.UserMapper;
import com.elyashevich.store.repository.UserRepository;
import com.elyashevich.store.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(SignUpDto signUpDto ) throws java.io.IOException {
        final List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        final User user = userMapper.convert(signUpDto, roles, "");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("CREATE NEW USER\n" + user);
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        log.info("FIND USER BY USERNAME\n");
        return userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException(String.format("User with username = '%s' wasn't found!", username))
        );
    }

    @Override
    public User createAdmin(SignUpDto signUpDto) {
        final List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        roles.add(Role.ROLE_ADMIN);
        final User user = userMapper.convert(signUpDto, roles, "");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("CREATE NEW ADMIN\n" + user);
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        log.info("FIND USER BY EMAIL");
        return userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(String.format("User with email = '%s' wasn't found!", email))
        );
    }

    @Override
    public User update(String id, UserUpdateDto userUpdateDto) {
        final User user = findById(id);
        if (!passwordEncoder.matches(userUpdateDto.password(), user.getPassword())) {
            throw  new BadRequestException("Password mismatch");
        }
        user.setBalance(userUpdateDto.balance());
        user.setEmail(userUpdateDto.email());
        user.setUsername(userUpdateDto.username());
        log.info("UPDATE USER\n" + user);
        return userRepository.save(user);
    }

    @Override
    public User findById(String id) {
        log.info("FIND USER BY ID");
        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("User with id = '%s' wasn't found!", id))
        );
    }

    @Override
    public List<User> findAll(String q) throws RuntimeException {
        log.info("FIND ALL USERS");
        if (!q.isEmpty()) {
            List<User> users = userRepository.findByQuery(q);
            if (users.isEmpty()) {
                throw new NotFoundException(String.format("User with username = '%s' wasn't found!", q));
            }
            return users;
        }
        return userRepository.findAll();
    }

    @Override
    public void delete(String id) {
        final User user = findById(id);
        log.info("DELETE USER\n" + user);
        userRepository.delete(user);
    }
}