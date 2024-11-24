package com.pms.clubmanagmentsystem.Repository;

import com.pms.clubmanagmentsystem.Entity.EventsAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventAttendanceRepository  extends JpaRepository<EventsAttendance, Long> {
}
