package se.iths.parking_lot.services;


import se.iths.parking_lot.exceptions.ParkingLotNotFoundException;
import se.iths.parking_lot.exceptions.ParkingSlotNotFoundException;
import se.iths.parking_lot.exceptions.UserNotFoundException;

import java.util.List;

public interface CRUDService<T> {
    void create(T t);

    void updateWithPUT(T t);

    void updateWithPATCH(T t) throws UserNotFoundException, ParkingSlotNotFoundException, ParkingLotNotFoundException;

    List<T> getAll();

    T getById(Long id) throws UserNotFoundException, ParkingSlotNotFoundException, ParkingLotNotFoundException;

    void remove(Long id) throws UserNotFoundException;
}
