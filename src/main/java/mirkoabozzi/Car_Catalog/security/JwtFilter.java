package mirkoabozzi.Car_Catalog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mirkoabozzi.Car_Catalog.entities.User;
import mirkoabozzi.Car_Catalog.exceptions.UnauthorizedException;
import mirkoabozzi.Car_Catalog.services.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTTools jwtTools;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) throw new UnauthorizedException("Token required.");

        String token = header.substring(7);
        this.jwtTools.verifyToken(token);

        String id = this.jwtTools.extractIdFromToken(token);
        User userFound = this.userService.findById(UUID.fromString(id));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userFound, null, userFound.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path = request.getServletPath();
        List<String> patternList = List.of(
                "/api/auth/**",
                "/swagger-ui/**",
                "/v3/api-docs/**"
        );
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        return patternList.stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }
}
