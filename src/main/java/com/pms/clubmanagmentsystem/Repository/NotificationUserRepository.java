package com.pms.clubmanagmentsystem.Repository;

import com.pms.clubmanagmentsystem.Entity.Event;
import com.pms.clubmanagmentsystem.Entity.NotificationUser;
import com.pms.clubmanagmentsystem.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationUserRepository extends JpaRepository<NotificationUser, Long> {
    List<NotificationUser> findByUser(User user);
    List<NotificationUser> findByEvent(Event event);
}
