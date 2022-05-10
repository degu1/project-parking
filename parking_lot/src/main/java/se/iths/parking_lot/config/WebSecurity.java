package se.iths.parking_lot.config;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import se.iths.parking_lot.entities.User;

@Component("webSecurity")
public class WebSecurity {

    public boolean checkUserId(Authentication authentication, Long id) {

        try {
            User principal = (User) authentication.getPrincipal();
            return principal.getId().equals(id);
        } catch (Exception e) {
            return false;
        }
    }
}
