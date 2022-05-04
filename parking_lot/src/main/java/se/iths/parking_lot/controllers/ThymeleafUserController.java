package se.iths.parking_lot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.parking_lot.dtos.AddUserToQueueDto;
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
    public String userById(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        User user = userService.getById(id);
        List<ParkingLot> parkingLots = parkingLotService.getAll();
        AddUserToQueueDto addUserToQueueDto = new AddUserToQueueDto(-1L, false);
        model.addAttribute("user", user);
        model.addAttribute("parkingLots", parkingLots);
        model.addAttribute("addUserToQueueDto", addUserToQueueDto);
        return "user_by_id";
    }

    @GetMapping("{userId}/queueSlots/{slotId}/remove")
    public String removeQueueSlots(@PathVariable("userId") Long userId, @PathVariable("slotId") Long slotId, Model model) {
        userService.removeQueueSlot(slotId);
        return "redirect:/tl_users/{userId}";
    }

    @GetMapping("{userId}/parkingSlots/{slotId}/remove")
    public String removeParkingSlots(@PathVariable("userId") Long userId, @PathVariable("slotId") Long slotId, Model model) throws ParkingSlotNotFoundException {
        parkingSlotService.removeUserFromParkingSlot(slotId);
        return "redirect:/tl_users/{userId}";
    }

    @PostMapping("{userId}/queue")
    public String addUserToQueue(@PathVariable("userId") Long userId, @ModelAttribute AddUserToQueueDto addUserToQueueDto, Model model) throws UserNotFoundException, ParkingLotNotFoundException {
        model.addAttribute("dto", addUserToQueueDto);
        userService.queryToParkingLot(userId, addUserToQueueDto.getParkingLotId(), addUserToQueueDto.getElectricCharge());
        return "redirect:/tl_users/{userId}";
    }

    @GetMapping("{userId}/edit")
    public String editUser(@PathVariable("userId") Long userId, Model model) throws UserNotFoundException {
        User user = userService.getById(userId);
        model.addAttribute("user", user);
        return "user_edit";
    }

    @PostMapping("{userId}/edit/submit")
    public String editSubmit(@ModelAttribute User user, @PathVariable("userId") Long userId, Model model) {
        userService.updateWithPUT(user);
        return "redirect:/tl_users/{userId}";
    }
}
