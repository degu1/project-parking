package se.iths.parking_lot.JMS.sender;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import se.iths.parking_lot.JMS.config.MessageQueueConfig;
import se.iths.parking_lot.JMS.models.MessageObject;
import se.iths.parking_lot.JMS.models.MessageTopic;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.Queue;
import se.iths.parking_lot.entities.QueueSlot;

import java.util.*;

@Component
public class MessageSender {

    private final JmsTemplate jmsTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Gson gson;

    public MessageSender(JmsTemplate jmsTemplate, GsonBuilder gson) {
        this.jmsTemplate = jmsTemplate;
        this.gson = gson.setPrettyPrinting().create();
    }

    public void addedToParkingSlotMessage(ParkingSlot parkingSlot) {
        if(parkingSlot.getUser().getEmailNotification()) {
            logger.info("Sending added to parking message...");

            List<MessageObject> messageObjectList = convertParkingSlotToMessage(parkingSlot);

            jmsTemplate.convertAndSend(MessageQueueConfig.PARKING_LOT_EMAIL, gson.toJson(messageObjectList));
            logger.info("Message sent!");
        }
    }

    public void addedToQueueMessage(QueueSlot queueSlot) {
        if(queueSlot.getUser().getEmailNotification()) {
            logger.info("Sending added to queue message...");

            List<MessageObject> messageObjectList = convertQueueSlotToMessage(queueSlot);

            jmsTemplate.convertAndSend(MessageQueueConfig.PARKING_LOT_EMAIL, gson.toJson(messageObjectList));
            logger.info("Message sent!");
        }
    }

    public void sendQueueUpdateMessage(Queue queue) {
        logger.info("Sending queue update message...");

        List<MessageObject> messages = convertQueueToMessage(queue);

        jmsTemplate.convertAndSend(MessageQueueConfig.PARKING_LOT_EMAIL, gson.toJson(messages));
        logger.info("Message sent!");
    }

    private List<MessageObject> convertParkingSlotToMessage(ParkingSlot parkingSlot) {
        return List.of(new MessageObject(
                UUID.randomUUID(),
                MessageTopic.IN_PARKING,
                parkingSlot.getUser().getName(),
                parkingSlot.getUser().getEmail(),
                parkingSlot.getParkingLot().getName(),
                parkingSlot.getName(),
                null));
    }

    private List<MessageObject> convertQueueSlotToMessage(QueueSlot queueSlot) {
        return List.of(new MessageObject(
                UUID.randomUUID(),
                MessageTopic.IN_QUEUE,
                queueSlot.getUser().getName(),
                queueSlot.getUser().getEmail(),
                queueSlot.getQueue().getParkingLot().getName(),
                null,
                queueSlot.getQueue().getQueuePosition(queueSlot)));
    }

    private List<MessageObject> convertQueueToMessage(Queue queue) {
        List<MessageObject> messageObjectList = new ArrayList<>();
        queue.getQueueSlots()
                .stream()
                .filter(queueSlot -> queueSlot.getUser().getEmailNotification())
                .forEach(queueSlot ->
                        messageObjectList.add(new MessageObject(
                                UUID.randomUUID(),
                                MessageTopic.QUEUE_UPDATED,
                                queueSlot.getUser().getName(),
                                queueSlot.getUser().getEmail(),
                                queueSlot.getQueue().getParkingLot().getName(),
                                null,
                                queueSlot.getQueue().getQueuePosition(queueSlot))));
        return messageObjectList;
    }
}
