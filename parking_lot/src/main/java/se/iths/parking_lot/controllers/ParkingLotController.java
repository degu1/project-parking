package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.parking_lot.dtos.ParkingLotDto;
import se.iths.parking_lot.services.ParkingLotService;

@RestController
@RequestMapping("parking_lot")


public class ParkingLotController extends CRUDController<ParkingLotDto, ParkingLotService> {

    public ParkingLotController(ParkingLotService parkingLotService) {
        super.service = parkingLotService;
    }
}
