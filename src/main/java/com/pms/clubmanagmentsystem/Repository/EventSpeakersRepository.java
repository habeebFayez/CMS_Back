package com.pms.clubmanagmentsystem.Repository;

import com.pms.clubmanagmentsystem.Entity.Event;
import com.pms.clubmanagmentsystem.Entity.EventSpeakers;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventSpeakersRepository  extends JpaRepository<EventSpeakers, Long> {

    List<EventSpeakers> findByEvent(Event category);
    EventSpeakers deleteByEvent(Event event);

}
