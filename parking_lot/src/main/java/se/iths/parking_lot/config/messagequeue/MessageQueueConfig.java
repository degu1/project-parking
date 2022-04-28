package se.iths.parking_lot.config.messagequeue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import se.iths.parking_lot.entities.ParkingSlot;

import javax.naming.Binding;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class MessageQueueConfig {

    public static final String IN_PARKING = "Added to parking";
    public static final String IN_QUEUE = "Added to queue";
    public static final String QUEUE_UPDATED = "Queue updated";

    @Bean
    public MessageConverter messageConverter() {

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");

        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule timeModule = new JavaTimeModule();

        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        objectMapper.registerModule(timeModule);
        converter.setObjectMapper(objectMapper);

        return converter;
    }

}
