package com.pms.clubmanagmentsystem.dto;

import com.pms.clubmanagmentsystem.Entity.NotificationUser;

public class AdminRequests {

    private Long clubID;
    private Long eventID;
    private NotificationUser notificationUser;

    public Long getClubID() {
        return clubID;
    }

    public void setClubID(Long clubID) {
        this.clubID = clubID;
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public NotificationUser getNotification() {
        return notificationUser;
    }

    public void setNotification(NotificationUser notificationUser) {
        this.notificationUser = notificationUser;
    }
}
