package se.iths.parking_lot.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import se.iths.parking_lot.config.JwtUtil;
import se.iths.parking_lot.dtos.AuthRequestDto;
import se.iths.parking_lot.entities.User;
import se.iths.parking_lot.services.MyUserDetailsService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Controller
@CrossOrigin
public class JwtAuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private MyUserDetailsService userDetailsService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, MyUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(value = "/authenticate")
    public String createAuthToken(AuthRequestDto authRequestDto, HttpServletResponse response) throws Exception {
        authenticate(authRequestDto.username(), authRequestDto.password());
        final User user = userDetailsService
                .loadUserByUsername(authRequestDto.username());
        final String token = jwtUtil.generateToken(user);

        Cookie cookie = new Cookie("token", token);
        cookie.isHttpOnly();
        cookie.setSecure(true);
        cookie.setMaxAge(5000);
        response.addCookie(cookie);

        return "redirect:/tl_users/" + user.getId();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
