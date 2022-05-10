package se.iths.parking_lot.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthException {

    @ExceptionHandler(ExpiredJwtException.class)
    public String logout() {
        return "redirect:/logout";
    }
}
