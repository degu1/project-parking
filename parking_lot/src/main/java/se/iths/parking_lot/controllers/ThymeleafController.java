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

import static se.iths.parking_lot.utils.EntityMapper.parkingLotToDto;
import static se.iths.parking_lot.utils.EntityMapper.parkingSlotToDto;


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
    public String edit(@PathVariable("id") Long id, @RequestParam(name = "constrainException", defaultValue = "false") Boolean constrainException, Model model) throws ParkingLotNotFoundException {
        ParkingLotDto parkingLotDto = parkingLotToDto(parkingLotService.getById(id));
        model.addAttribute("parkingLot", parkingLotDto);
        model.addAttribute("constrainException", constrainException);
        return "thymeleaf_edit_parking_lot";
    }

    @PostMapping("/edit")
    public String submitEdit(@ModelAttribute ParkingLot parkingLot, Model model) throws ParkingLotNotFoundException {
        try {
            parkingLotService.updateWithPATCH(parkingLot);
        } catch (DataIntegrityViolationException e) {
            return "redirect:/tl_parking_lots/edit/" + parkingLot.getId() + "?constrainException=true";
        }
        return "redirect:/tl_parking_lots";
    }

    @GetMapping("/{id}")
    public String slots(@PathVariable("id") Long id, Model model) throws ParkingLotNotFoundException {
        ParkingLotDto parkingLotDto = parkingLotToDto(parkingLotService.getById(id));
        model.addAttribute("parkingLot", parkingLotDto);
        return "thymeleaf_parking_slots";
    }

    @GetMapping("/{lotId}/slots/{slotId}/remove")
    public String removeSlots(@PathVariable("lotId") Long lotId, @PathVariable("slotId") Long slotId, Model model) throws ParkingSlotNotFoundException {
        parkingSlotService.removeUserFromParkingSlot(slotId);
        return "redirect:/tl_parking_lots/{lotId}";
    }

    @GetMapping("/{lotId}/slot/{id}/edit")
    public String editParkingSlot(@PathVariable("id") Long id, @PathVariable("lotId") Long lotId, Model model) throws ParkingSlotNotFoundException {
        ParkingSlotDto parkingSlotDto = parkingSlotToDto(parkingSlotService.getById(id));
        model.addAttribute("parkingSlot", parkingSlotDto);
        model.addAttribute("lotId", lotId);
        return "thymeleaf_edit_parking_slot";
    }

    @PostMapping("/{lotId}/slot/edit")
    public String submitEditSlot(@ModelAttribute ParkingSlot parkingSlot, @PathVariable("lotId") Long lotId, Model model) throws ParkingSlotNotFoundException {
        model.addAttribute("parkingSlot", parkingSlot);
        parkingSlotService.updateWithPATCH(parkingSlot);
        return "redirect:/tl_parking_lots/{lotId}";
    }

    @PostMapping("{lotId}/slots/add")
    public String addSubmit(@ModelAttribute ParkingSlot parkingSlot, @PathVariable("lotId") Long lotId, Model model) throws ParkingLotNotFoundException {
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
