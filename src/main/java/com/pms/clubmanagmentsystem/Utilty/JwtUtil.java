package com.pms.clubmanagmentsystem.Utilty;
//validator + more checks

import com.pms.clubmanagmentsystem.Entity.User;
import com.pms.clubmanagmentsystem.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {//used for filtering the token by getting token data

    @Autowired
    private UserRepository userRepository;
    //5hours validity
    public static final long JwtTokenValidity = 5 * 60 * 60;
    private final String SECRET_KEY = "**********";

    public String getEmailFromTohen(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String userEmail = userDetails.getUsername();

        // Fetch roles from the database for the given username
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            claims.put("roles", user.getAuthority().getAuthorityName());
        } else {
          return null;
        }
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * JwtTokenValidity))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    //should apply it to get the username that will be used to loing "EMAIL"
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getEmailFromTohen(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}