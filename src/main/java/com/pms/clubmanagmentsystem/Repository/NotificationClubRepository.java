package com.pms.clubmanagmentsystem.Repository;

import com.pms.clubmanagmentsystem.Entity.Club;
import com.pms.clubmanagmentsystem.Entity.NotificationClub;
import com.pms.clubmanagmentsystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationClubRepository extends JpaRepository<NotificationClub, Long> {
    List<NotificationClub> findByUser(User user);
    List<NotificationClub> findByClub(Club club);
}
