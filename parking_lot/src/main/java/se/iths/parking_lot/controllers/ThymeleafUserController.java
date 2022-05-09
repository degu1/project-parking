package se.iths.parking_lot.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.parking_lot.dtos.AddUserToQueueDto;
import se.iths.parking_lot.dtos.UserDto;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.User;
import se.iths.parking_lot.exceptions.ParkingLotNotFoundException;
import se.iths.parking_lot.exceptions.ParkingSlotNotFoundException;
import se.iths.parking_lot.exceptions.UserNotFoundException;
import se.iths.parking_lot.services.ParkingLotService;
import se.iths.parking_lot.services.ParkingSlotService;
import se.iths.parking_lot.services.UserService;

import java.util.List;

@Controller
@RequestMapping("tl_users")
public class ThymeleafUserController {

    private final UserService userService;
    private final ParkingSlotService parkingSlotService;
    private final ParkingLotService parkingLotService;

    public ThymeleafUserController(UserService userService, ParkingSlotService parkingSlotService, ParkingLotService parkingLotService) {
        this.userService = userService;
        this.parkingSlotService = parkingSlotService;
        this.parkingLotService = parkingLotService;
    }

    @GetMapping("{id}")
    public String userById(@CookieValue("token") String token, @PathVariable("id") Long id, Model model) {
        User currentUser = userService.getUserByToken(token);
        if (id.equals(currentUser.getId())) {
            List<ParkingLot> parkingLots = parkingLotService.getAll();
            AddUserToQueueDto addUserToQueueDto = new AddUserToQueueDto(-1L, false);
            model.addAttribute("user", currentUser);
            model.addAttribute("parkingLots", parkingLots);
            model.addAttribute("addUserToQueueDto", addUserToQueueDto);
            return "user_by_id";
        } else {
            return "redirect:/redirect";
        }
    }

    @GetMapping("{userId}/queueSlots/{slotId}/remove")
    public String removeQueueSlots(@CookieValue("token") String token, @PathVariable("userId") Long userId, @PathVariable("slotId") Long slotId, Model model) {
        User currentUser = userService.getUserByToken(token);
        if (userId.equals(currentUser.getId())) {
            userService.removeQueueSlot(slotId);
            return "redirect:/tl_users/{userId}";
        } else {
            return "redirect:/redirect";
        }
    }

    @GetMapping("{userId}/parkingSlots/{slotId}/remove")
    public String removeParkingSlots(@CookieValue("token") String token, @PathVariable("userId") Long userId, @PathVariable("slotId") Long slotId, Model model) throws ParkingSlotNotFoundException {
        User currentUser = userService.getUserByToken(token);
        if (userId.equals(currentUser.getId())) {
            parkingSlotService.removeUserFromParkingSlot(slotId);
            return "redirect:/tl_users/{userId}";
        } else {
            return "redirect:/redirect";
        }
    }

    @PostMapping("{userId}/queue")
    public String addUserToQueue(@CookieValue("token") String token, @PathVariable("userId") Long userId, @ModelAttribute AddUserToQueueDto addUserToQueueDto, Model model) throws UserNotFoundException, ParkingLotNotFoundException {
        User currentUser = userService.getUserByToken(token);
        if (userId.equals(currentUser.getId())) {
            model.addAttribute("dto", addUserToQueueDto);
            userService.queryToParkingLot(userId, addUserToQueueDto.parkingLotId(), addUserToQueueDto.electricCharge());
            return "redirect:/tl_users/{userId}";
        } else {
            return "redirect:/redirect";
        }
    }

    @GetMapping("{userId}/edit")
    public String editUser(@CookieValue("token") String token, @PathVariable("userId") Long userId, @RequestParam(name = "constrainException", defaultValue = "false") Boolean constrainException, Model model) throws UserNotFoundException {
        User currentUser = userService.getUserByToken(token);
        if (userId.equals(currentUser.getId())) {
            User user = userService.getById(userId);
            model.addAttribute("user", user);
            model.addAttribute("constrainException", constrainException);
            return "user_edit";
        } else {
            return "redirect:/redirect";
        }
    }

    @PostMapping("{userId}/edit/submit")
    public String editSubmit(@ModelAttribute UserDto userDto, @PathVariable("userId") Long userId) throws UserNotFoundException {
        try {
            userService.updateWithPATCH(userDto.toUser());
        } catch (
                DataIntegrityViolationException e) {
            return "redirect:/tl_users/{userId}/edit?constrainException=true";
        }
        return "redirect:/tl_users/{userId}";
    }

    @GetMapping("create")
    public String createUser(@RequestParam(name = "constrainException", defaultValue = "false") Boolean constrainException, Model model) {
        model.addAttribute("constrainException", constrainException);
        model.addAttribute("user", new User());
        return "user_create";
    }

    @PostMapping("create")
    public String createUserSubmit(UserDto userDto) {
        try {
            userService.create(userDto.toUser());
        } catch (
                DataIntegrityViolationException e) {
            return "redirect:/tl_users/create?constrainException=true";
        }
        return "redirect:/tl_users/create";
    }
}
