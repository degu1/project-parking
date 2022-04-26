package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.*;
import se.iths.parking_lot.dtos.ParkingLotDto;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.services.ParkingLotService;

import java.util.List;

import static se.iths.parking_lot.EntityMapper.parkingLotToDto;

@RestController
@RequestMapping("parking_lots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService service) {
        this.parkingLotService = service;
    }

    @PostMapping
    public void create(@RequestBody ParkingLot parkingLot) {
        parkingLotService.create(parkingLot);
    }

    @PutMapping
    public void updateWithPUT(@RequestBody ParkingLot parkingLot) {
        parkingLotService.updateWithPUT(parkingLot);
    }

    @PatchMapping
    public void updateWithPATCH(@RequestBody ParkingLot parkingLot) {
        parkingLotService.updateWithPATCH(parkingLot);
    }

    @GetMapping
    public List<ParkingLotDto> getAll() {
        return parkingLotToDto(parkingLotService.getAll());
    }

    @GetMapping("{id}")
    public ParkingLotDto getById(@PathVariable("id")Long id) {
        return parkingLotToDto(parkingLotService.getById(id));
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Long id) {
        parkingLotService.remove(id);
    }

    @PutMapping("remove_parking_slot")
    public void removeParkingSlot(Long parkingLotId, Long parkingSlotId) {
        parkingLotService.removeParkingSlot(parkingLotId,parkingSlotId);
    }

}
