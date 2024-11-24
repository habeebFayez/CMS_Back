package com.pms.clubmanagmentsystem.Repository;

import com.pms.clubmanagmentsystem.Entity.Event;
import com.pms.clubmanagmentsystem.Entity.EventSponsors;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventSponsorsRepository  extends JpaRepository<EventSponsors, Long> {

    List<EventSponsors> findByEvent(Event category);
    EventSponsors deleteByEvent(Event event);

}
