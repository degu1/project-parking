package se.iths.parking_lot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDto(Long id, String name, String email, Boolean emailNotification) {

}
