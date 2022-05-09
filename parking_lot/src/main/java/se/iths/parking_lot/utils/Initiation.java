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


            User user = new User("dennis@iths.se", "$2a$12$rdQiDVu2ke.OABKJxdrDc.BbfdvyGbEncuL3YfPAQIn4gphKt7S9u", new ArrayList<>());
            user.setName("Dennis");
            user.setRoles(roles);
            userService.create(user);


            User user1 = new User("jonas@iths.se", "$2a$12$rdQiDVu2ke.OABKJxdrDc.BbfdvyGbEncuL3YfPAQIn4gphKt7S9u", new ArrayList<>());
            user1.setName("Jonas");
            user1.setRoles(List.of(managerRole));
            userService.create(user1);

            User user2 = new User("milad@iths.se", "$2a$12$rdQiDVu2ke.OABKJxdrDc.BbfdvyGbEncuL3YfPAQIn4gphKt7S9u", new ArrayList<>());
            user2.setName("Milad");
            user2.setRoles(List.of(userRole));
            userService.create(user2);
        }

    }
}
