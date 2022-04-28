package com.example.message_receiver.receiver;

import com.example.message_receiver.config.MessageQueueConfig;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import se.iths.parking_lot.model.MessageObject;

@Component
public class MessageReceiver {

    @JmsListener(destination = MessageQueueConfig.IN_PARKING)
    public void listen(@Payload MessageObject messageObject) {
        System.out.println("I got a message");
        System.out.println(messageObject);
    }
}