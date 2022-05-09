package se.iths.parking_lot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.iths.parking_lot.entities.ParkingLot;

@JsonIgnoreProperties
public record ParkingLotDto(Long id, String name) {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ParkingLot toParkingLot() {
        return objectMapper.convertValue(this, ParkingLot.class);
    }
}
