package com.pms.clubmanagmentsystem.Entity;

import javax.persistence.*;
@Entity
@Table(name = "EventSponsors")
public class EventSponsors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventSponsorsID;
    @ManyToOne
    @JoinColumn(name = "event_id" , nullable = false)
    private Event event;
    @Column( nullable = false)
    private String name;
    @Column(length = 255)
    private String contactNumber;

    public Long getEventSponsorsID() {
        return eventSponsorsID;
    }

    public void setEventSponsorsID(Long eventSponsorsID) {
        this.eventSponsorsID = eventSponsorsID;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String sponsorsName) {
        this.name = sponsorsName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
