package com.ameda.api.messaging;


import com.ameda.api.user.User;
import com.ameda.api.user.UserServiceImpl;
import com.ameda.shared.messagingConfig.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Author: kev.Ameda
 */
@Service
public class MessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    private final UserServiceImpl userService;

    public MessageConsumer(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public <T> void receiveMessage(User user){
        log.info("Inside consumer Name: {} from City:{}",user.getName(),user.getAddress().getCity());
        log.info("Persisting data to Database...");
        userService.createUserFromQueue(user);
    }

}
