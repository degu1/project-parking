package se.iths.message_receiver.receiver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import se.iths.message_receiver.config.MessageQueueConfig;
import se.iths.message_receiver.models.MessageObject;
import se.iths.message_receiver.services.EmailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Component
public class MessageReceiver {

    private final Gson gson;
    private final EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MessageReceiver(Gson gson, EmailService emailService) {
        this.gson = gson;
        this.emailService = emailService;
    }

    @JmsListener(destination = MessageQueueConfig.PARKING_LOT_EMAIL)
    public void inParkingReceiver(@Payload String message) {

        List<MessageObject> messageObjectList =
                gson.fromJson(message, new TypeToken<List<MessageObject>>() {}.getType());

        for (MessageObject messageObject : messageObjectList) {
            logger.info("Received message " + messageObject.getMessageTopic() + ": " + messageObject);
            logger.info("Sending Email to: " + messageObject.getUserEmail() + " ...");

            try {
                emailService.sendEmail(messageObject);
            } catch (MessagingException | IOException e) {
                logger.error(e.getMessage());
            }

            logger.info("Email sent to user: " + messageObject.getUserName() + "!");
        }
    }
}