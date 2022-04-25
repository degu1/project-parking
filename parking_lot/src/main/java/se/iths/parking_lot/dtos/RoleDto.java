package se.iths.parking_lot.dtos;

import se.iths.parking_lot.entities.Role;
import se.iths.parking_lot.entities.Type;
import se.iths.parking_lot.entities.User;

import java.util.List;

public record RoleDto(Type type, List<User> users) {

    public static RoleDto from(Role role) {
        return new RoleDto(role.getType(), role.getUsers());
    }

    public static List<RoleDto> from(List<Role> roles) {
        return roles.stream()
                .map(RoleDto::from).toList();
    }

}
