package se.iths.parking_lot;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.iths.parking_lot.dtos.ParkingLotDto;
import se.iths.parking_lot.dtos.ParkingSlotDto;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.ParkingSlot;

import java.util.List;

public class EntityMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static ParkingLotDto parkingLotToDto(ParkingLot parkingLot) {
        return objectMapper.convertValue(parkingLot, ParkingLotDto.class);
    }

    public static List<ParkingLotDto> parkingLotToDto(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                .map(EntityMapper::parkingLotToDto)
                .toList();
    }

}
