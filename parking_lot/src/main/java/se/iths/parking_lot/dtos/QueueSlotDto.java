package se.iths.parking_lot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record QueueSlotDto(Long id, Boolean electricCharge, UserDto user) {

}
