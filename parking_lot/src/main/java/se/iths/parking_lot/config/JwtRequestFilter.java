package se.iths.parking_lot.config;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import se.iths.parking_lot.entities.User;
import se.iths.parking_lot.exceptions.NoCookieException;
import se.iths.parking_lot.services.MyUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(MyUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwt(request);
            String username = jwtUtil.extractUsername(jwt);
            User user = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.doAuthenticationNotExist() && jwtUtil.isTokenNotExpired(jwt)) {
                SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken(request, user));
            }
        } catch (NoCookieException | IllegalArgumentException | ExpiredJwtException ignored) {
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken userAuthenticationToken(HttpServletRequest request, User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }

    private String getJwt(HttpServletRequest request) throws NoCookieException {
        if (request.getCookies() == null) {
            throw new NoCookieException();
        }
        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("token"))
                .map(Cookie::getValue)
                .findFirst().orElseThrow(NoCookieException::new);
    }
}
