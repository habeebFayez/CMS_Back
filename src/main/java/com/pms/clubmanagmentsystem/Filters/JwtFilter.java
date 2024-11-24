package com.pms.clubmanagmentsystem.Filters;

import com.pms.clubmanagmentsystem.Repository.UserRepository;
import com.pms.clubmanagmentsystem.Utilty.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//this class is a filter by JWT principals that i will user with spring security
@Component
public class JwtFilter extends OncePerRequestFilter {// helps spring security check for authentication as filtering the data sent as JASON
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // 1. get the authentication header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 2. validate the header and check the prefix
        if (header == null || !header.startsWith("Bearer ")) {///????????
            chain.doFilter(request, response);        // If not valid, go to the next filter.
            return;
        }

        // If there is no token provided and hence the user won't be authenticated.
        // It's Ok. Maybe the user acces sing a public path or asking for a token.

        // All secured paths that needs a token are already defined and secured in config class.
        // And If user tried to access without access token, then he won't be authenticated and an exception will be thrown.

        // 3. Get the token & user details % validate
        // split Authorization -> [Bearer][encoded token as(5ergerhgdb$%^$EHDNYFtyj65nh..ect)
        final String token = header.split(" ")[1].trim();
        UserDetails userDetails = userRepository.findByEmail(jwtUtil.getEmailFromTohen(token));

        if (!jwtUtil.validateToken(token, userDetails)) {
            chain.doFilter(request, response);
            return;

        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails == null ?
                List.of() : userDetails.getAuthorities());
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

}
