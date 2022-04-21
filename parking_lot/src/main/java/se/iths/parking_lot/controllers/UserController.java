package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.parking_lot.dtos.UserDto;
import se.iths.parking_lot.services.UserService;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController implements CRUDController<UserDto> {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void create(UserDto userDto) {
        userService.create(userDto.toUser());
    }

    @Override
    public void updateWithPUT(UserDto userDto) {
        userService.updateWithPUT(userDto.toUser());
    }

    @Override
    public void updateWithPATCH(UserDto userDto) {
        userService.updateWithPATCH(userDto.toUser());
    }

    @Override
    public List<UserDto> getAll() {
        return UserDto.from(userService.getAll());
    }

    @Override
    public UserDto getById(Long id) {
        return UserDto.from(userService.getById(id));
    }

    @Override
    public void remove(Long id) {
        userService.remove(id);
    }
}
