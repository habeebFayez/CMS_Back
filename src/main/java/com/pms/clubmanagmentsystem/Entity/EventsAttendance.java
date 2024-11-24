package com.pms.clubmanagmentsystem.Entity;


import javax.persistence.*;

@Entity
@Table(name = "eventsAttendance")
public class EventsAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventsAttendanceID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    public Long getEventsAttendanceID() {
        return eventsAttendanceID;
    }

    public void setEventsAttendanceID(Long eventsAttendanceID) {
        this.eventsAttendanceID = eventsAttendanceID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
