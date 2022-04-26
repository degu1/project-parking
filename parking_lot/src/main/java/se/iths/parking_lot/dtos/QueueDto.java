package se.iths.parking_lot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record QueueDto( Long id, List<QueueSlotDto> queueSlots) {

}
