package se.iths.parking_lot.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import se.iths.parking_lot.dtos.AuthRequestDto;
import se.iths.parking_lot.entities.User;
import se.iths.parking_lot.services.UserService;

@Controller
public class LoginController {


    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("login")
    public String viewLoginPage(Model model) {
        if (isAuthenticated()) {
            return "redirect:/redirect";
        }
        AuthRequestDto authRequest = new AuthRequestDto(null, null);
        model.addAttribute("authRequest", authRequest);
        return "login";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
    @GetMapping("redirect")
    public String homePage(@CookieValue("token") String token){
        User currentUser = userService.getUserByToken(token);
        return "redirect:/tl_users/"+currentUser.getId();
    }
}
