package se.iths.parking_lot.services;

import org.springframework.stereotype.Service;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.QueueSlot;
import se.iths.parking_lot.entities.User;
import se.iths.parking_lot.repositories.ParkingLotRepository;
import se.iths.parking_lot.repositories.QueueSlotRepository;
import se.iths.parking_lot.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class UserService implements CRUDService<User> {

    private final UserRepository userRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final QueueSlotRepository queueSlotRepository;

    public UserService(UserRepository userRepository, ParkingLotRepository parkingLotRepository, QueueSlotRepository queueSlotRepository) {
        this.userRepository = userRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.queueSlotRepository = queueSlotRepository;
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateWithPUT(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateWithPATCH(User user) {
        User oldUser = userRepository.findById(user.getId()).orElseThrow();//TODO
        if (user.getName().equals(null)) {
            oldUser.setName(user.getName());
        }
        if (user.getEmail().equals(null)) {
            oldUser.setEmail(user.getEmail());
        }

        userRepository.save(oldUser);
    }

    @Override
    public List<User> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .toList();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow();//TODO
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    public void queryToParkingLot(Long userId, Long parkingLotId, Boolean electricCharge) {
        User user = userRepository.findById(userId).orElseThrow(); // TODO
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(); // TODO

        QueueSlot queueSlot = queueSlotRepository.save(new QueueSlot(user, electricCharge));

        Boolean isFirstInQueue = parkingLot.getQueue().addSlotToQueue(queueSlot);

        try {
            ParkingSlot parkingSlot = parkingLot.emptyParkingSlot(electricCharge);
            if(isFirstInQueue){
                parkingSlot.setUser(user);
                queueSlotRepository.delete(queueSlot);
            }
        } catch (Exception e) {
            System.out.println("First in QUEUE!");
        }
    }


    public void removeFromParkingLot(Long userId, Long queueSlotId) {
        User user = userRepository.findById(userId).orElseThrow(); // TODO
        QueueSlot queueSlot = queueSlotRepository.findById(queueSlotId).orElseThrow(); // TODO

        user.removeQueueSlot(queueSlot);
    }
}
