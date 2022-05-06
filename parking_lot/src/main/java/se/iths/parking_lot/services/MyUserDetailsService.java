package se.iths.parking_lot.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.iths.parking_lot.entities.User;
import se.iths.parking_lot.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email != null) {
            return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        } else {
            throw new UsernameNotFoundException("Email is empty.");
        }
    }
}
