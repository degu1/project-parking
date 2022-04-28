package se.iths.parking_lot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ParkingSlotDto(Long id, String name, Boolean electricCharge, UserDto user) {

}
