package com.pms.clubmanagmentsystem.Entity;



import javax.persistence.*;

@Entity
@Table(name = "EventSpeakers")
public class EventSpeakers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventSpeakersID;
    @ManyToOne
    @JoinColumn(name = "event_id" , nullable = false)
    private Event event;
    @Column( nullable = false)
    private String name;
    @Column(length = 255)
    private String contactNumber;
    public Long getEventSpeakersID() {
        return eventSpeakersID;
    }

    public void setEventSpeakersID(Long eventSpeakersID) {
        this.eventSpeakersID = eventSpeakersID;
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

    public void setName(String speakersName) {
        this.name = speakersName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
