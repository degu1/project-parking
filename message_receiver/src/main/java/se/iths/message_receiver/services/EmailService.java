package se.iths.message_receiver.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import se.iths.message_receiver.models.MessageObject;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    public EmailService(JavaMailSender javaMailSender, MailContentBuilder mailContentBuilder) {
        this.javaMailSender = javaMailSender;
        this.mailContentBuilder = mailContentBuilder;
    }

    public void sendEmail(MessageObject messageObject) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(messageObject.getUserEmail());
        helper.setSubject(messageObject.getMessageTopic().toString());
        helper.setText(mailContentBuilder.build(messageObject), true);

        javaMailSender.send(msg);
    }

}
