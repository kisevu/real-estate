package com.ameda.api.messaging;

import com.ameda.shared.MessagePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: kev.Ameda
 */
@RestController
@RequestMapping("/messaging")
public class MessagingResource {

    private static final Logger log = LoggerFactory.getLogger(MessagingResource.class);

    private final MessageProducer messageProducer;

    public MessagingResource(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping
    public String send(@RequestBody MessagePayload messagePayload){
        messageProducer.sendMessage(messagePayload);
        return "message sent!";
    }

}
