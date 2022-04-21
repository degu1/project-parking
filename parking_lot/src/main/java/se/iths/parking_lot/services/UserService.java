package se.iths.parking_lot.services;

import org.springframework.stereotype.Service;
import se.iths.parking_lot.entities.User;
import se.iths.parking_lot.repositories.UserRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserService implements CRUDService<User> {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
