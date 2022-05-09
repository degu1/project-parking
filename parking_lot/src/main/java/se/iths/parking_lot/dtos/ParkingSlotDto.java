package se.iths.parking_lot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.iths.parking_lot.entities.ParkingSlot;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ParkingSlotDto(Long id, String name, Boolean electricCharge) {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ParkingSlot toParkingSlot() {
        return objectMapper.convertValue(this, ParkingSlot.class);
    }
}
