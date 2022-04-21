package se.iths.parking_lot.repositories;

import org.springframework.data.repository.CrudRepository;
import se.iths.parking_lot.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
}
