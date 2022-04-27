package se.iths.parking_lot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.services.ParkingLotService;
import se.iths.parking_lot.services.ParkingSlotService;

@Controller
@RequestMapping("tl_parking_lots")
public class ThymeleafController {

    ParkingLotService parkingLotService;
    ParkingSlotService parkingSlotService;

    public ThymeleafController(ParkingLotService parkingLotService, ParkingSlotService parkingSlotService) {
        this.parkingLotService = parkingLotService;
        this.parkingSlotService = parkingSlotService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("parkingLots", parkingLotService.getAll());
        return "thymeleaf_parking_lot";
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("parkingLot", new ParkingLot());
        return "thymeleaf_add_parking_lot";
    }

    @PostMapping("add")
    public String add(@ModelAttribute ParkingLot parkingLot, Model model) {
        model.addAttribute(parkingLot);
        parkingLotService.create(parkingLot);
        return "redirect:/tl_parking_lots";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        parkingLotService.remove(id);
        return "redirect:/tl_parking_lots";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("parkingLot", parkingLotService.getById(id));
        return "thymeleaf_edit_parking_lot";
    }

    @PostMapping("/edit")
    public String submitEdit(@ModelAttribute ParkingLot parkingLot, Model model) {
        model.addAttribute("parkingLot", parkingLot);
        parkingLotService.updateWithPATCH(parkingLot);
        return "redirect:/tl_parking_lots";
    }

    @GetMapping("/{id}")
    public String slots(@PathVariable("id") Long id, Model model) {
        ParkingLot parkingLot = parkingLotService.getById(id);
        model.addAttribute("parkingLot", parkingLot);
        model.addAttribute("parkingLotName", parkingLot.getName());
        model.addAttribute("parkingSlots", parkingLot.getParkingSlots());
        return "thymeleaf_parking_slots";
    }

    @GetMapping("/{lotId}/slots/{slotId}/remove")
    public String removeSlots(@PathVariable("lotId") Long lotId, @PathVariable("slotId") Long slotId, Model model) {
        parkingSlotService.removeUserFromParkingSlot(slotId);
        return "redirect:/tl_parking_lots/{lotId}";
    }

    @GetMapping("/{lotId}/slot/{id}/edit")
    public String editParkingSlot(@PathVariable("id") Long id, @PathVariable("lotId") Long lotId, Model model) {
        ParkingSlot parkingSlot = parkingSlotService.getById(id);
        model.addAttribute("parkingSlot", parkingSlot);
        model.addAttribute("lotId", lotId);
        return "thymeleaf_edit_parking_slot";
    }

    @PostMapping("/{lotId}/slot/edit")
    public String submitEditSlot(@ModelAttribute ParkingSlot parkingSlot, @PathVariable("lotId") Long lotId, Model model) {
        model.addAttribute("parkingSlot", parkingSlot);
        parkingSlotService.updateWithPATCH(parkingSlot);
        return "redirect:/tl_parking_lots/{lotId}";
    }

    @PostMapping("{lotId}/slots/add")
    public String addSubmit(@ModelAttribute ParkingSlot parkingSlot, @PathVariable("lotId") Long lotId, Model model) {
        model.addAttribute("parkingSlot", parkingSlot);
        parkingSlotService.create(parkingSlot, lotId);
        return "redirect:/tl_parking_lots/{lotId}";
    }

    @GetMapping("{lotId}/slots/add")
    public String add(@ModelAttribute ParkingSlot parkingSlot, @PathVariable("lotId") Long lotId, Model model) {
        model.addAttribute("parkingSlot", parkingSlot);
        model.addAttribute("lotId", lotId);
        return "thymeleaf_add_parking_slot";
    }

    @GetMapping("{lotId}/slot/{slotId}/delete")
    public String deleteSlot(@PathVariable("lotId") Long lotId, @PathVariable("slotId") Long slotId) {
        parkingSlotService.remove(slotId);
        return "redirect:/tl_parking_lots/{lotId}";
    }
}
