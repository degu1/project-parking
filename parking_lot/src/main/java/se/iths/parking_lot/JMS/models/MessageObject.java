package se.iths.parking_lot.JMS.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
public class MessageObject {

    private UUID id;
    private MessageTopic messageTopic;
    private String eventTime;
    private String userName;
    private String userEmail;
    private String parkingLot;
    private String parkingSlot;
    private Long queuePosition;

    public MessageObject(UUID id, MessageTopic messageTopic, String userName, String userEmail, String parkingLot, String parkingSlot, Long queuePosition) {
        this.id = id;
        this.messageTopic = messageTopic;
        this.eventTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm:ss"));
        this.userName = userName;
        this.userEmail = userEmail;
        this.parkingLot = parkingLot;
        this.parkingSlot = parkingSlot;
        this.queuePosition = queuePosition;
    }
}
