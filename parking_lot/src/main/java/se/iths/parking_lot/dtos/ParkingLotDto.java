package se.iths.parking_lot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ParkingLotDto(Long id, String name, List<ParkingSlotDto> parkingSlots, QueueDto queue) {

}
