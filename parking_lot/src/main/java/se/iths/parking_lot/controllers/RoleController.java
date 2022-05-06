package se.iths.parking_lot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.iths.parking_lot.dtos.RoleDto;
import se.iths.parking_lot.services.RoleService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static se.iths.parking_lot.utils.EntityMapper.roleToDto;

@RestController
@RequestMapping("role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public List<RoleDto> getAll() {
        return roleToDto(roleService.getAll());
    }

    @GetMapping("check-role")
    public String checkRole(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){

            }
        }
    }
}
