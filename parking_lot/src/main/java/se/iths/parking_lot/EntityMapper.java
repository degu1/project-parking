package se.iths.parking_lot;

import com.fasterxml.jackson.databind.ObjectMapper;
import se.iths.parking_lot.dtos.ParkingLotDto;
import se.iths.parking_lot.dtos.ParkingSlotDto;
import se.iths.parking_lot.dtos.RoleDto;
import se.iths.parking_lot.dtos.UserDto;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.Role;
import se.iths.parking_lot.entities.User;

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

    public static ParkingSlotDto parkingSlotToDto(ParkingSlot parkingSlot) {
        return objectMapper.convertValue(parkingSlot, ParkingSlotDto.class);
    }

    public static List<ParkingSlotDto> parkingSlotToDto(List<ParkingSlot> parkingSlot) {
        return parkingSlot.stream()
                .map(EntityMapper::parkingSlotToDto)
                .toList();
    }

    public static RoleDto roleToDto(Role role) {
        return objectMapper.convertValue(role, RoleDto.class);
    }

    public static List<RoleDto> roleToDto(List<Role> roles) {
        return roles.stream()
                .map(EntityMapper::roleToDto)
                .toList();
    }

    public static UserDto userToDto(User user) {
        return objectMapper.convertValue(user, UserDto.class);
    }

    public static List<UserDto> userToDto(List<User> users) {
        return users.stream()
                .map(EntityMapper::userToDto)
                .toList();
    }

}
