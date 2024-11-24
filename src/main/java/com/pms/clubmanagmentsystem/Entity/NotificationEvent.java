package com.pms.clubmanagmentsystem.Entity;

import javax.persistence.*;
import java.sql.Date;
@Entity
@Table(name = "NotificationEvent")
public class NotificationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;
    @ManyToOne
    @JoinColumn(name = "club_id",nullable = false)
    private Club club;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @Column(length = 500)
    private String notificationMessage;
    @Column(nullable = false)
    private String notificationType;
    @Column(nullable = false)
    private boolean readStatus;
    private Date CreationDate;

    public Long getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(Long navigationID) {
        this.notificationID = navigationID;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String navigationMessage) {
        this.notificationMessage = navigationMessage;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String navigationType) {
        this.notificationType = navigationType;
    }

    public boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(boolean readStatus) {
        this.readStatus = readStatus;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(Date creationDate) {
        CreationDate = creationDate;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
//Navigations messages , userID ,  readStatus ,navigationType ,