package se.iths.parking_lot.repositories;

import org.springframework.data.repository.CrudRepository;
import se.iths.parking_lot.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
