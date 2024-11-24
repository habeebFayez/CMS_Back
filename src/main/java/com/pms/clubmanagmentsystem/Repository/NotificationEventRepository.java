package com.pms.clubmanagmentsystem.Repository;


import com.pms.clubmanagmentsystem.Entity.Club;
import com.pms.clubmanagmentsystem.Entity.Event;
import com.pms.clubmanagmentsystem.Entity.NotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationEventRepository extends JpaRepository<NotificationEvent, Long> {

    List<NotificationEvent> findByEvent(Event event);
    List<NotificationEvent> findByClub(Club club);

}
