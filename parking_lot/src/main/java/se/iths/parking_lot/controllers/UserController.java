package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.*;
import se.iths.parking_lot.dtos.UserDto;
import se.iths.parking_lot.entities.User;
import se.iths.parking_lot.services.UserService;

import java.util.List;

import static se.iths.parking_lot.EntityMapper.userToDto;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void create(@RequestBody User user) {
        userService.create(user);
    }

    @PutMapping
    public void updateWithPUT(@RequestBody User user) {
        userService.updateWithPUT(user);
    }

    @PatchMapping
    public void updateWithPATCH(@RequestBody User user) {
        userService.updateWithPATCH(user);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userToDto(userService.getAll());
    }

    @GetMapping("{id}")
    public UserDto getById(@PathVariable("id") Long id) {
        return userToDto(userService.getById(id));
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") Long id) {
        userService.remove(id);
    }

    @PutMapping("{userId}/subscribe")
    public void queueToParkingLot(@PathVariable("userId") Long userId, Long parkingLotId, Boolean electricCharge) {
        userService.queryToParkingLot(userId, parkingLotId, electricCharge);
    }

    @PutMapping("{userId}/unsubscribe")
    public void removeFromParkingLot(@PathVariable("userId") Long userId, Long queueSlotId) {
        userService.removeFromParkingLot(userId,queueSlotId);
    }
}
