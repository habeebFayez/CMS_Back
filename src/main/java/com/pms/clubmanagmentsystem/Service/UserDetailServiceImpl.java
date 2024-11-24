package com.pms.clubmanagmentsystem.Service;


import com.pms.clubmanagmentsystem.Entity.User;
import com.pms.clubmanagmentsystem.Repository.AuthorityRepository;
import com.pms.clubmanagmentsystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    private final Long ROLE_ADMIN = 1L;
    private final Long ROLE_STUDENT = 2L;
    private final Long ROLE_MANAGER = 3L;



    public List<User> getAllUsers() throws UsernameNotFoundException {
        return userRepository.findAll();
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No such User");
        }
        return (user);
    }


    public boolean isExistingUser(String email) {

        User user = userRepository.findByEmail(email);

        return user != null;
    }

    public boolean isExistingStudentnumber(String studentNumber) {

        User user = userRepository.findByStudentNumber(studentNumber);

        return user != null;
    }

    public User regesterNewUser(User user) throws UsernameNotFoundException {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setStudentNumber(user.getStudentNumber());
        newUser.setAuthority(authorityRepository.getById(ROLE_STUDENT));
        newUser.setActive(true);
        return userRepository.save(newUser);
    }
    public User updateUser(User updatedUser , User authUser) throws UsernameNotFoundException {

        User existingUser = userRepository.findById(authUser.getUserID())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setStudentNumber(updatedUser.getStudentNumber());
        if(updatedUser.getProfilePicURL()!=null){
            existingUser.setProfilePicURL(updatedUser.getProfilePicURL());
        }
        return userRepository.save(existingUser);
    }


    public boolean verifyUser(String email, String otp) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user.getOtp() != null && user.getOtp().equals(otp)) {
            user.setActive(true);
            userRepository.save(user);
            return true;
        }

        return false;
    }
    public boolean areCredentialsValid(String email, String password) {
        // Retrieve user details from your data store (e.g., database)
        User user = userRepository.findByEmail(email);

        if (user != null) {
            // Compare the provided password with the stored password hash
            return passwordEncoder.matches(password, user.getPassword());
        }

        return false; // User not found or incorrect email
    }
    public boolean updateResetPassword(String token ,String email){
        User user = userRepository.findByEmail(email);
        if(user != null){
            user.setOtp(token);
            userRepository.save(user);
            return true;
        }
        return false;
    }
    public User getUserToResetPassword(String token){
        return userRepository.findByResetPasswordToken(token);
    }
    public void updatePassword(User updatedUser , User authUser){
        User existingUser = userRepository.findById(authUser.getUserID())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String password= updatedUser.getLastName();
        existingUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(existingUser);
    }
    public void updateAuthority(User updatedUser , User authUser){
        User existingUser = userRepository.findById(authUser.getUserID())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
       existingUser.setAuthority(authorityRepository.getById(ROLE_MANAGER));
        userRepository.save(existingUser);
    }

}




