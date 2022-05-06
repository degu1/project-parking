package se.iths.parking_lot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.iths.parking_lot.model.AuthRequest;

@Controller
public class LoginController {
    @GetMapping("login")
    public String viewLoginPage(Model model) {
        AuthRequest authRequest = new AuthRequest();
        model.addAttribute("authRequest", authRequest);
        return "login";
    }
}
