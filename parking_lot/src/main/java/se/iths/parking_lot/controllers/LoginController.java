package se.iths.parking_lot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.iths.parking_lot.dtos.AuthRequestDto;

@Controller
public class LoginController {
    @GetMapping("login")
    public String viewLoginPage(Model model) {
        AuthRequestDto authRequestDto = new AuthRequestDto(null, null);
        model.addAttribute("authRequest", authRequestDto);
        return "login";
    }
}
