package com.pms.clubmanagmentsystem.Controller;

import com.pms.clubmanagmentsystem.Entity.User;
import com.pms.clubmanagmentsystem.Repository.UserRepository;
import com.pms.clubmanagmentsystem.Service.UserDetailServiceImpl;
import com.pms.clubmanagmentsystem.Utilty.JwtUtil;
import com.pms.clubmanagmentsystem.dto.AuthCredentialsRequest ;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredentialsRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),request.getPassword()
                    ));
            User user = (User) authentication.getPrincipal();
            if(user.isActive()) {
                return ResponseEntity.ok().header(
                                HttpHeaders.AUTHORIZATION,jwtUtil.generateToken(user))
                        .body(user);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        } catch (BadCredentialsException ex ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody User user) {

        if (userDetailService.isExistingUser(user.getEmail()) ||
                userDetailService.isExistingStudentnumber(user.getStudentNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        userDetailService.regesterNewUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/updateUser")
    public ResponseEntity<?> upDateUser(@AuthenticationPrincipal User authUser ,
                                        @RequestBody User user) {
        User existingUser = userDetailService.loadUserByUsername(authUser.getEmail());

        if(!existingUser.getStudentNumber().equals(user.getStudentNumber()) &&
                (userDetailService.isExistingStudentnumber(user.getStudentNumber()))) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Student number already exists");

        }


        userDetailService.updateUser(user,authUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal User user ) {
        User existingUser = userDetailService.loadUserByUsername(user.getEmail());
        return ResponseEntity.ok(existingUser);
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@AuthenticationPrincipal User authUser ,
                                           @RequestBody User user) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authUser.getEmail(),user.getPassword()
                    ));
            User existingUser = (User) authentication.getPrincipal();
            userDetailService.updatePassword(user,existingUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (BadCredentialsException ex ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }


    }

//    @PostMapping("/verify")
//    public ResponseEntity<String> verify(@RequestBody VerificationRequest request) {
//        boolean response = userDetailService.verifyUser(request.getEmail(), request.getOpt());
//        if (response) {
//            return ResponseEntity.ok("You have been verified.");
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Code. Please try again.");
//    }


    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token
                                           ) {
       try{
           User user = userDetailService.loadUserByUsername(jwtUtil.getEmailFromTohen(token));
           Boolean isTokenValid =jwtUtil.validateToken(token,user);
           return ResponseEntity.ok(isTokenValid);
       }catch (ExpiredJwtException ex){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

       }


    }

}
