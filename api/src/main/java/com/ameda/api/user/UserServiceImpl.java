package com.ameda.api.user;

import com.ameda.api.messaging.MessageProducer;
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
    private final MessageProducer messageProducer;


    public UserServiceImpl(UserRepository userRepository,
                           MessageProducer messageProducer) {
        this.userRepository = userRepository;
        this.messageProducer = messageProducer;
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
        log.info("Sending data to rabbit queue: {}","sent!!!");
        messageProducer.sendMessage(user);
        return user;
    }

    public User createUserFromQueue(User user){
        return userRepository.save(user);
    }


    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
