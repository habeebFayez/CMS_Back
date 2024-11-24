package com.pms.clubmanagmentsystem.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventID;
    @Column(length = 500)
    private String eventLocationURL;
    @Column(length = 255 , nullable = false)
    private String eventName;
    @ManyToOne(optional = false)
    @JoinColumn(name = "club_id") // each event can be associated with one and only one club
    private Club club; // i need club ID to connect with event

//    @OneToMany(mappedBy = "event" ,cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<EventCategory> eventCategories;
    private Date eventCreationDate;
    private Date eventStartingDate;
    @Column(length = 1500)
    private String eventDescription;
    @Column(length = 1500)
    private String eventPostDescription;
    @Column(length = 10)
    private int eventLikes;
    @Column(length = 500)
    private String eventPostMediaURL;
    @Column(length = 50)
    private String eventNote;
    private String eventEndTime;
    private boolean eventStates;
    private boolean eventisRejected;
    private boolean eventUpdated;
    private boolean eventPostRequested;
    @Column(length = 255)
    private String eventHall;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public String getEventLocationURL() {
        return eventLocationURL;
    }

    public void setEventLocationURL(String eventLocationURL) {
        this.eventLocationURL = eventLocationURL;
    }

    public Club getClubID() {
        return club;
    }

    public void setClubID(Club clubID) {
        this.club = clubID;
    }

    public Date getEventCreationDate() {
        return eventCreationDate;
    }

    public void setEventCreationDate(Date eventCreationDate) {
        this.eventCreationDate = eventCreationDate;
    }

    public Date getEventStartingDate() {
        return eventStartingDate;
    }

    public void setEventStartingDate(Date eventStartingDate) {
        this.eventStartingDate = eventStartingDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public int getEventLikes() {
        return eventLikes;
    }

    public void setEventLikes(int eventLikes) {
        this.eventLikes = eventLikes;
    }

    public String getEventPostMediaURL() {
        return eventPostMediaURL;
    }

    public void setEventPostMediaURL(String eventPostMediaURL) {
        this.eventPostMediaURL = eventPostMediaURL;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getEventNote() {
        return eventNote;
    }

    public void setEventNote(String eventNote) {
        this.eventNote = eventNote;
    }

    public boolean isEventStates() {
        return eventStates;
    }

    public void setEventStates(boolean eventStates) {
        this.eventStates = eventStates;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public boolean isEventUpdated() {
        return eventUpdated;
    }

    public void setEventUpdated(boolean eventUpdated) {
        this.eventUpdated = eventUpdated;
    }

    public String getEventHall() {
        return eventHall;
    }

    public void setEventHall(String eventHall) {
        this.eventHall = eventHall;
    }

    public String getEventPostDescription() {
        return eventPostDescription;
    }

    public void setEventPostDescription(String eventPostDescription) {
        this.eventPostDescription = eventPostDescription;
    }

    public boolean isEventPostRequested() {
        return eventPostRequested;
    }

    public void setEventPostRequested(boolean eventPostRequested) {
        this.eventPostRequested = eventPostRequested;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public boolean isEventisRejected() {
        return eventisRejected;
    }

    public void setEventisRejected(boolean eventisRejected) {
        this.eventisRejected = eventisRejected;
    }

    //    @JsonIgnore
//    public List<EventCategory> getEventCategories() {
//        return eventCategories;
//    }
//
//    public void setEventCategories(List<EventCategory> eventCategories) {
//        this.eventCategories = eventCategories;
//    }
}
