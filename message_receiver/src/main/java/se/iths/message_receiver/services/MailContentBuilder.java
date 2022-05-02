package se.iths.message_receiver.services;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import se.iths.message_receiver.models.MessageObject;

@Service
public class MailContentBuilder {

    private final TemplateEngine templateEngine;

    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(MessageObject messageObject) {
        Context context = new Context();
        context.setVariable("messageObject", messageObject);
        return templateEngine.process(messageObject.getMessageTopic().toString() + "_MAIL", context);
    }

}
