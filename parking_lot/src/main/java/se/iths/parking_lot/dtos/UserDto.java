package se.iths.parking_lot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.iths.parking_lot.entities.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDto(Long id, String name, String email, Boolean emailNotification) {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public User toUser() {
        return objectMapper.convertValue(this, User.class);
    }
}
