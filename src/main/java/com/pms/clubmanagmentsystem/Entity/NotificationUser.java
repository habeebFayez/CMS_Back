package com.pms.clubmanagmentsystem.Entity;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "NotificationUser")
public class NotificationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
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
}
//Navigations messages , userID ,  readStatus ,navigationType ,