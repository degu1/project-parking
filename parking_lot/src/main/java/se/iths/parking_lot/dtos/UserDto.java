package se.iths.parking_lot.dtos;

import se.iths.parking_lot.entities.QueueSlot;
import se.iths.parking_lot.entities.User;

import java.util.List;

public record UserDto(Long id, String name, String email, List<QueueSlotDto> queueSlots) {

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(),QueueSlotDto.from(user.getQueueSlots()));
    }

    public static List<UserDto> from(List<User> users) {
        return users.stream()
                .map(user -> UserDto.from(user)).toList();
    }

    public User toUser() {
        return User.fromDto(this);
    }
}
