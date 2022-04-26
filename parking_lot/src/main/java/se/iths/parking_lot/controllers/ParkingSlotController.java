package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.*;
import se.iths.parking_lot.dtos.ParkingSlotDto;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.services.ParkingSlotService;

import java.util.List;

import static se.iths.parking_lot.EntityMapper.parkingSlotToDto;

@RestController
@RequestMapping("parking_slots")
public class ParkingSlotController {

    ParkingSlotService parkingSlotservice;

    public ParkingSlotController(ParkingSlotService service) {
        this.parkingSlotservice = service;
    }


    @PostMapping("{parkingLotId}")
    public void create(@RequestBody ParkingSlot parkingSlot, @PathVariable("parkingLotId") Long parkingLotId) {
        parkingSlotservice.create(parkingSlot, parkingLotId);
    }

    @PutMapping
    public void updateWithPUT(@RequestBody ParkingSlot parkingSlot) {
        parkingSlotservice.updateWithPUT(parkingSlot);
    }

    @PatchMapping
    public void updateWithPATCH(@RequestBody ParkingSlot parkingSlot) {
        parkingSlotservice.updateWithPATCH(parkingSlot);
    }

    @GetMapping
    public List<ParkingSlotDto> getAll() {
        return parkingSlotToDto(parkingSlotservice.getAll());
    }

    @GetMapping("{id}")
    public ParkingSlotDto getById(@PathVariable("id") Long id) {
        return parkingSlotToDto(parkingSlotservice.getById(id));
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable("id") Long id) {
        parkingSlotservice.remove(id);
    }

    @PutMapping("{parkingSlotId}/remove_user")
    public void removeUserFromParkingSlot(@PathVariable("parkingSlotId") Long parkingSlotId) {
        parkingSlotservice.removeUserFromParkingSlot(parkingSlotId);
    }
}
