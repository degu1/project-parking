package se.iths.parking_lot.dtos;

import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.User;

import java.util.List;

public record ParkingSlotDto(Long id, String name, Boolean electricCharge, UserDto user) {

    public static ParkingSlotDto from(ParkingSlot parkingSlot) {
        if(parkingSlot.getUser() != null) {
            return new ParkingSlotDto(parkingSlot.getId(), parkingSlot.getName(), parkingSlot.getElectricCharge(), UserDto.from(parkingSlot.getUser()));
        }
        return new ParkingSlotDto(parkingSlot.getId(), parkingSlot.getName(), parkingSlot.getElectricCharge(), null);
    }

    public ParkingSlot toParkingLot() {
        return ParkingSlot.from(this);
    }

    public static List<ParkingSlotDto> from(List<ParkingSlot> parkingSlots) {
        return parkingSlots.stream()
                .map(ParkingSlotDto::from).toList();
    }

    public ParkingSlotDto(Long id, String name, Boolean electricCharge, UserDto user) {
        this.id = id;
        this.name = name;
        this.user = user;
        if(electricCharge == null){
            this.electricCharge = false;
        } else {
            this.electricCharge = electricCharge;
        }
    }
}
