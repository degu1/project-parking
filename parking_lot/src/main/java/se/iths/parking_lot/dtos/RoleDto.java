package se.iths.parking_lot.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.iths.parking_lot.entities.Type;
import se.iths.parking_lot.entities.User;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RoleDto(Type type, List<User> users) {

}
