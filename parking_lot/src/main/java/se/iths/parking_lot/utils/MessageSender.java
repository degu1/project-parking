package se.iths.parking_lot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import se.iths.parking_lot.config.messagequeue.MessageQueueConfig;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.QueueSlot;
import se.iths.parking_lot.model.MessageObject;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class MessageSender {

    private final JmsTemplate jmsTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void addedToParkingSlotMessage(ParkingSlot parkingSlot) {
        logger.info("Sending added to parking message...");
        MessageObject messageObject = new MessageObject(UUID.randomUUID(),"Hello from JU20DEC_QUEUE!", LocalDateTime.now());
        jmsTemplate.convertAndSend(MessageQueueConfig.IN_PARKING, messageObject);
        logger.info("Message sent!");
    }

    public void addedToQueueMessage(QueueSlot queueSlot) {
        logger.info("Sending added to queue message...");
        jmsTemplate.convertAndSend(MessageQueueConfig.IN_QUEUE, queueSlot);
        logger.info("Message sent!");
    }

    public void sendQueueUpdateMessage(QueueSlot queueSlot) {
        logger.info("Sending queue update message...");
        jmsTemplate.convertAndSend(MessageQueueConfig.QUEUE_UPDATED, queueSlot);
        logger.info("Message sent!");
    }
}
