package se.iths.parking_lot.services;

import org.springframework.stereotype.Service;
import se.iths.parking_lot.entities.Role;
import se.iths.parking_lot.repositories.RoleRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll() {
        return StreamSupport.stream(roleRepository.findAll().spliterator(), false).toList();
    }

    public void create(Role role) {
        roleRepository.save(role);
    }

    public Role findByRoleName(String role) {
        return roleRepository.findByType(role);
    }
}
