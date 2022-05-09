package se.iths.parking_lot.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.parking_lot.dtos.ParkingLotDto;
import se.iths.parking_lot.dtos.ParkingSlotDto;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.exceptions.ParkingLotNotFoundException;
import se.iths.parking_lot.exceptions.ParkingSlotNotFoundException;
import se.iths.parking_lot.services.ParkingLotService;
import se.iths.parking_lot.services.ParkingSlotService;

@Controller
@RequestMapping("tl_parking_lots")
public class ThymeleafController {

    private final ParkingLotService parkingLotService;
    private final ParkingSlotService parkingSlotService;

    public ThymeleafController(ParkingLotService parkingLotService, ParkingSlotService parkingSlotService) {
        this.parkingLotService = parkingLotService;
        this.parkingSlotService = parkingSlotService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("parkingLots", parkingLotService.getAll());
        return "thymeleaf_parking_lots";
    }

    @GetMapping("add")
    public String add(ParkingLot parkingLot, @RequestParam(name = "constrainException", defaultValue = "false") Boolean constrainException, Model model) {
        model.addAttribute("parkingLot", new ParkingLot());
        model.addAttribute("constrainException", constrainException);
        return "thymeleaf_add_parking_lot";
    }

    @PostMapping("add")
    public String add(@ModelAttribute ParkingLotDto parkingLotDto) {
        try {
            parkingLotService.create(parkingLotDto.toParkingLot());
        } catch (DataIntegrityViolationException e) {
            return "redirect:/tl_parking_lots/add?constrainException=true";
        }
        return "redirect:/tl_parking_lots";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        parkingLotService.remove(id);
        return "redirect:/tl_parking_lots";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, @RequestParam(name = "constrainException", defaultValue = "false") Boolean constrainException, Model model) throws ParkingLotNotFoundException {
        ParkingLot parkingLot = parkingLotService.getById(id);
        model.addAttribute("parkingLot", parkingLot);
        model.addAttribute("constrainException", constrainException);
        return "thymeleaf_edit_parking_lot";
    }

    @PostMapping("/edit")
    public String submitEdit(@ModelAttribute ParkingLotDto parkingLotDto) throws ParkingLotNotFoundException {
        try {
            parkingLotService.updateWithPATCH(parkingLotDto.toParkingLot());
        } catch (DataIntegrityViolationException e) {
            return "redirect:/tl_parking_lots/edit/" + parkingLotDto.id() + "?constrainException=true";
        }
        return "redirect:/tl_parking_lots";
    }

    @GetMapping("/{id}")
    public String slots(@PathVariable("id") Long id, Model model) throws ParkingLotNotFoundException {
        ParkingLot parkingLot = parkingLotService.getById(id);
        model.addAttribute("parkingLot", parkingLot);
        return "thymeleaf_parking_slots";
    }

    @GetMapping("/{lotId}/slots/{slotId}/remove")
    public String removeSlots(@PathVariable("lotId") Long lotId, @PathVariable("slotId") Long slotId) throws ParkingSlotNotFoundException {
        parkingSlotService.removeUserFromParkingSlot(slotId);
        return "redirect:/tl_parking_lots/{lotId}";
    }

    @GetMapping("/{lotId}/slot/{id}/edit")
    public String editParkingSlot(@PathVariable("id") Long id, @PathVariable("lotId") Long lotId, @RequestParam(name = "constrainException", defaultValue = "false") Boolean constrainException, Model model) throws ParkingSlotNotFoundException {
        ParkingSlot parkingSlot = parkingSlotService.getById(id);
        model.addAttribute("parkingSlot", parkingSlot);
        model.addAttribute("lotId", lotId);
        model.addAttribute("constrainException", constrainException);
        return "thymeleaf_edit_parking_slot";
    }

    @PostMapping("/{lotId}/slot/edit")
    public String submitEditSlot(@ModelAttribute ParkingSlotDto parkingSlotDto, @PathVariable("lotId") Long lotId) throws ParkingSlotNotFoundException {
        try {
            parkingSlotService.updateWithPATCH(parkingSlotDto.toParkingSlot());
        } catch (DataIntegrityViolationException e) {
            return "redirect:/tl_parking_lots/{lotId}/slot/" + parkingSlotDto.id() + "/edit?constrainException=true";
        }
        return "redirect:/tl_parking_lots/{lotId}";
    }

    @PostMapping("{lotId}/slots/add")
    public String addSubmit(@ModelAttribute ParkingSlotDto parkingSlotDto, @PathVariable("lotId") Long lotId, Model model) throws ParkingLotNotFoundException {
        try {
            parkingSlotService.create(parkingSlotDto.toParkingSlot(), lotId);
        } catch (DataIntegrityViolationException e) {
            return "redirect:/tl_parking_lots/{lotId}/slots/add?constrainException=true";
        }
        return "redirect:/tl_parking_lots/{lotId}";
    }

    @GetMapping("{lotId}/slots/add")
    public String add(@ModelAttribute ParkingSlot parkingSlot, @PathVariable("lotId") Long lotId, @RequestParam(name = "constrainException", defaultValue = "false") Boolean constrainException, Model model) {
        model.addAttribute("parkingSlot", parkingSlot);
        model.addAttribute("lotId", lotId);
        model.addAttribute("constrainException", constrainException);
        return "thymeleaf_add_parking_slot";
    }

    @GetMapping("{lotId}/slot/{slotId}/delete")
    public String deleteSlot(@PathVariable("lotId") Long lotId, @PathVariable("slotId") Long slotId) {
        parkingSlotService.remove(slotId);
        return "redirect:/tl_parking_lots/{lotId}";
    }
}
