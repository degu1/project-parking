package se.iths.parking_lot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import se.iths.parking_lot.config.messagequeue.MessageQueueConfig;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.QueueSlot;

@Component
public class MessageSender {

    private final JmsTemplate jmsTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void addedToParkingSlotMessage(ParkingSlot parkingSlot) {
        logger.info("Sending added to parking message...");
        jmsTemplate.convertAndSend(MessageQueueConfig.IN_PARKING, parkingSlot);
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
