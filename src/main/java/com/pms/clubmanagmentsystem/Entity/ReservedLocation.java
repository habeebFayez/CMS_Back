package com.pms.clubmanagmentsystem.Entity;

import javax.persistence.*;

@Entity
@Table(name = "ReservedLocation")

public class ReservedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservedLocationID;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    private String reservedHall;

    public Long getReservedLocationID() {
        return reservedLocationID;
    }

    public void setReservedLocationID(Long reservedLocationID) {
        this.reservedLocationID = reservedLocationID;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getReservedHall() {
        return reservedHall;
    }

    public void setReservedHall(String reservedHall) {
        this.reservedHall = reservedHall;
    }
}
