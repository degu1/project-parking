package se.iths.parking_lot.utils;

import org.springframework.stereotype.Component;
import se.iths.parking_lot.entities.ParkingLot;
import se.iths.parking_lot.entities.ParkingSlot;
import se.iths.parking_lot.entities.Role;
import se.iths.parking_lot.entities.User;
import se.iths.parking_lot.exceptions.ParkingLotNotFoundException;
import se.iths.parking_lot.repositories.RoleRepository;
import se.iths.parking_lot.services.ParkingLotService;
import se.iths.parking_lot.services.ParkingSlotService;
import se.iths.parking_lot.services.RoleService;
import se.iths.parking_lot.services.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initiation {

    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final UserService userService;
    private final ParkingLotService parkingLotService;
    private final ParkingSlotService parkingSlotService;

    public Initiation(RoleRepository roleRepository, RoleService roleService, UserService userService, ParkingLotService parkingLotService, ParkingSlotService parkingSlotService) {
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.userService = userService;
        this.parkingLotService = parkingLotService;
        this.parkingSlotService = parkingSlotService;
    }


    @PostConstruct
    public void init() throws ParkingLotNotFoundException {
        if (roleService.getAll().isEmpty() && userService.getAll().isEmpty()) {
            Role userRole = new Role();
            userRole.setType("ROLE_USER");
            roleRepository.save(userRole);
            Role adminRole = new Role();
            adminRole.setType("ROLE_ADMIN");
            roleRepository.save(adminRole);
            Role managerRole = new Role();
            managerRole.setType("ROLE_MANAGER");
            roleRepository.save(managerRole);

            List<Role> roles = roleService.getAll();
            User user = new User("dennis.gustafsson@iths.se", "password", new ArrayList<>());
            user.setName("Dennis");
            user.setRoles(roles);
            userService.create(user);

            User user1 = new User("jonas.fredriksson@iths.se", "password", new ArrayList<>());
            user1.setName("Jonas");
            user1.setRoles(List.of(managerRole));
            userService.create(user1);

            User user2 = new User("milad.nazari@iths.se", "$2a$12$/ii6vlaGfNQmwz.3TpFitumtZKK0DyUayQKityjBCvLkk2z01huMK", new ArrayList<>());
            user2.setName("Milad");
            user2.setRoles(List.of(userRole));
            userService.create(user2);

            ParkingLot parkingLot = new ParkingLot();
            parkingLot.setName("Drottningsgatan 2, garage.");
            parkingLotService.create(parkingLot);

            ParkingLot parkingLot2 = new ParkingLot();
            parkingLot2.setName("Kungsgatan 8, garage");
            parkingLotService.create(parkingLot2);

            for (ParkingLot lot : parkingLotService.getAll()) {
                for (int i = 1; i <= 10; i++) {
                    ParkingSlot parkingSlot = new ParkingSlot();
                    parkingSlot.setName(String.valueOf(i));
                    if(i == 3 || i == 5 || i == 7){
                        parkingSlot.setElectricCharge(true);
                    }
                    parkingSlotService.create(parkingSlot, lot.getId());
                }
            }
        }
    }
}
