package com.ameda.api.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: kev.Ameda
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(UserDTO dto) {
        Address address = Address.builder()
                .street(dto.getAddress().getStreet())
                .city(dto.getAddress().getCity())
                .county(dto.getAddress().getCounty())
                .build();

        User user = User.builder()
                .name(dto.getName())
                .address(address)
                .build();
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
