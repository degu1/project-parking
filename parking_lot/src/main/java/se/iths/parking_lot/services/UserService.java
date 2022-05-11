package se.iths.parking_lot.services;

import org.springframework.stereotype.Service;
import se.iths.parking_lot.JMS.sender.MessageSender;
import se.iths.parking_lot.config.JwtUtil;
import se.iths.parking_lot.entities.*;
import se.iths.parking_lot.exceptions.NoEmptyParkingSlotException;
import se.iths.parking_lot.exceptions.ParkingLotNotFoundException;
import se.iths.parking_lot.exceptions.QueueSlotNotFoundException;
import se.iths.parking_lot.exceptions.UserNotFoundException;
import se.iths.parking_lot.repositories.ParkingLotRepository;
import se.iths.parking_lot.repositories.QueueSlotRepository;
import se.iths.parking_lot.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class UserService implements CRUDService<User> {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final QueueSlotRepository queueSlotRepository;
    private final MessageSender messageSender;

    public UserService(RoleService roleService, UserRepository userRepository, ParkingLotRepository parkingLotRepository, QueueSlotRepository queueSlotRepository, MessageSender messageSender) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.queueSlotRepository = queueSlotRepository;
        this.messageSender = messageSender;
    }

    @Override
    public void create(User user) {
        Role role = roleService.findByRoleName("ROLE_USER");
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public void updateWithPUT(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateWithPATCH(User user) throws UserNotFoundException {
        User oldUser = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("User with id " + user.getId() + " not found."));
        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }
        if (user.getEmailNotification() != null) {
            oldUser.setEmailNotification(user.getEmailNotification());
        }

        userRepository.save(oldUser);
    }

    @Override
    public List<User> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .toList();
    }

    @Override
    public User getById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
    }

    @Override
    public void remove(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
        user.getParkingSlots().forEach(ParkingSlot::removeUser);
        userRepository.deleteById(id);
    }

    public void queryToParkingLot(Long userId, Long parkingLotId, Boolean electricCharge) throws UserNotFoundException, ParkingLotNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId).orElseThrow(() -> new ParkingLotNotFoundException("Parking lot with id " + parkingLotId + " not found."));

        QueueSlot queueSlot = queueSlotRepository.save(new QueueSlot(user, electricCharge));

        Boolean isFirstInQueue = parkingLot.getQueue().addSlotToQueue(queueSlot);

        try {
            ParkingSlot parkingSlot = parkingLot.emptyParkingSlot(electricCharge);
            if (isFirstInQueue) {
                parkingSlot.setUser(user);
                queueSlotRepository.delete(queueSlot);
                messageSender.addedToParkingSlotMessage(parkingSlot);
            }
        } catch (NoEmptyParkingSlotException e) {
            messageSender.addedToQueueMessage(queueSlot);
        }
    }

    public void removeQueueSlot(Long id) {
        queueSlotRepository.deleteById(id);
    }

    public void removeFromQueueSlot(Long userId, Long queueSlotId) throws UserNotFoundException, QueueSlotNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
        QueueSlot queueSlot = queueSlotRepository.findById(queueSlotId).orElseThrow(() -> new QueueSlotNotFoundException("Queue slot with id " + queueSlotId + " not found"));

        user.removeQueueSlot(queueSlot);
    }

    public User getUserByToken(String token) {
        MyUserDetailsService userDetailsService = new MyUserDetailsService(userRepository);
        JwtUtil jwtUtil = new JwtUtil();
        return userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
    }
}
