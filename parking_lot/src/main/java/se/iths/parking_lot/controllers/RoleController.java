package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.parking_lot.config.JwtUtil;
import se.iths.parking_lot.dtos.RoleDto;
import se.iths.parking_lot.entities.User;
import se.iths.parking_lot.repositories.UserRepository;
import se.iths.parking_lot.services.RoleService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import java.util.List;

import static se.iths.parking_lot.utils.EntityMapper.roleToDto;

@RestController
@RequestMapping("role")
public class RoleController {

    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public RoleController(RoleService roleService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.roleService = roleService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public List<RoleDto> getAll() {
        return roleToDto(roleService.getAll());
    }

    @GetMapping("check-role")
    public String checkRole(HttpServletRequest request) throws ServiceException {
        String token = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }
        Long userId = 0L;
        if (!token.isEmpty()) {
            String email = jwtUtil.extractUsername(token);
            User user = userRepository.findByEmail(email).orElseThrow();
            userId = user.getId();
        }
        if(userId != 0L){
            return "redirect:/tl_users/" + userId;
        }else {
            throw new ServiceException("Did not find userId.");
        }
    }
}
