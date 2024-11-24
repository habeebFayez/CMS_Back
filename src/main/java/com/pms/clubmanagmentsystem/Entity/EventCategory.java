package com.pms.clubmanagmentsystem.Entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "EventCategory")
public class EventCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventCategoryID;
    @ManyToOne
    @JoinColumn(name = "event_id" , nullable = false)
    private Event event;
    @ManyToOne
    @JoinColumn(name = "category_id"  , nullable = false)
    private Category category;

    public Long getEventCategoryID() {
        return eventCategoryID;
    }

    public void setEventCategoryID(Long eventCategoryID) {
        this.eventCategoryID = eventCategoryID;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
