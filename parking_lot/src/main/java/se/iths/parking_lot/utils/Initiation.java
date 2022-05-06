package se.iths.parking_lot.utils;

import org.springframework.stereotype.Component;
import se.iths.parking_lot.entities.Role;
import se.iths.parking_lot.entities.Type;
import se.iths.parking_lot.entities.User;
import se.iths.parking_lot.repositories.RoleRepository;
import se.iths.parking_lot.repositories.UserRepository;
import se.iths.parking_lot.services.RoleService;
import se.iths.parking_lot.services.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Initiation {

    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final UserService userService;
    private final UserRepository userRepository;

    public Initiation(RoleRepository roleRepository, RoleService roleService, UserService userService, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void init() {
        if (roleService.getAll().isEmpty()) {
            Role userRole = new Role();
            userRole.setType(Type.USER);
            roleRepository.save(userRole);
            Role adminRole = new Role();
            adminRole.setType(Type.ADMIN);
            roleRepository.save(adminRole);
        }
        if(userService.getAll().isEmpty()){
            User user = new User("dennis@iths.se","$2a$12$rdQiDVu2ke.OABKJxdrDc.BbfdvyGbEncuL3YfPAQIn4gphKt7S9u", new ArrayList<>());
            User user1 = new User("jonas@iths.se", "$2a$12$rdQiDVu2ke.OABKJxdrDc.BbfdvyGbEncuL3YfPAQIn4gphKt7S9u", new ArrayList<>());
            User user2 = new User("milad@iths.se", "$2a$12$rdQiDVu2ke.OABKJxdrDc.BbfdvyGbEncuL3YfPAQIn4gphKt7S9u", new ArrayList<>());
            user.setName("Dennis");
            user1.setName("Jonas");
            user1.setName("Milad");
            userService.create(user);
            userService.create(user1);
            userService.create(user2);
        }

    }
}
