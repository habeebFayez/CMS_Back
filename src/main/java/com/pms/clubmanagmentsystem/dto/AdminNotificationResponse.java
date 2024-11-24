package com.pms.clubmanagmentsystem.dto;

import com.pms.clubmanagmentsystem.Entity.NotificationClub;
import com.pms.clubmanagmentsystem.Entity.NotificationEvent;
import com.pms.clubmanagmentsystem.Entity.NotificationUser;

import java.util.List;

public class AdminNotificationResponse {
    private List<NotificationUser> notificationUser;
    private List<NotificationClub> notificationClub;
    private List<NotificationEvent> notificationEvent;

    public List<NotificationUser> getNotificationUser() {
        return notificationUser;
    }

    public void setNotificationUser(List<NotificationUser> notificationUser) {
        this.notificationUser = notificationUser;
    }

    public List<NotificationClub> getNotificationClub() {
        return notificationClub;
    }

    public void setNotificationClub(List<NotificationClub> notificationClub) {
        this.notificationClub = notificationClub;
    }

    public List<NotificationEvent> getNotificationEvent() {
        return notificationEvent;
    }

    public void setNotificationEvent(List<NotificationEvent> notificationEvent) {
        this.notificationEvent = notificationEvent;
    }
}
