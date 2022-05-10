package se.iths.parking_lot.config;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import se.iths.parking_lot.entities.User;

@Component("webSecurity")
public class WebSecurity {

    public boolean checkUserId(Authentication authentication, Long id) {
        User principal = (User) authentication.getPrincipal();
        System.out.println(principal.getId() + " " + id);

        return principal.getId() == id;
    }
}
