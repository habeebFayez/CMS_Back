package com.pms.clubmanagmentsystem.Repository;


import com.pms.clubmanagmentsystem.Entity.Event;
import com.pms.clubmanagmentsystem.Entity.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventCategoryRepository  extends JpaRepository<EventCategory, Long> {

    List<EventCategory> findByEvent(Event category);
    EventCategory deleteByEvent(Event event);

}
