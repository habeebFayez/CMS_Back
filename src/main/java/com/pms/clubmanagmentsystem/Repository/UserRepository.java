package com.pms.clubmanagmentsystem.Repository;

import com.pms.clubmanagmentsystem.Entity.Authority;
import com.pms.clubmanagmentsystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


//CRUD methods
public interface UserRepository extends JpaRepository<User, Long> {

    //Optional if there is or not (i can actually use User and if not it will be null ...
    User findByEmail(String email);

    List<User> findAllByAuthority(Authority authority);

    User findByStudentNumber(String studentNumber);
    @Query("SELECT u FROM User u where u.otp =?1")
    User findByResetPasswordToken(String resetPasswordToken);
}
