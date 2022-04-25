package se.iths.parking_lot.utils;

import org.springframework.stereotype.Component;
import se.iths.parking_lot.entities.Role;
import se.iths.parking_lot.entities.Type;
import se.iths.parking_lot.repositories.RoleRepository;
import se.iths.parking_lot.services.RoleService;

import javax.annotation.PostConstruct;

@Component
public class Initiation {

    private final RoleRepository roleRepository;
    private final RoleService roleService;

    public Initiation(RoleRepository roleRepository, RoleService roleService) {
        this.roleRepository = roleRepository;
        this.roleService = roleService;
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
    }
}
