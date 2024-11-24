package com.pms.clubmanagmentsystem.dto;

import com.pms.clubmanagmentsystem.Entity.Category;
import com.pms.clubmanagmentsystem.Entity.Event;
import com.pms.clubmanagmentsystem.Entity.EventSpeakers;
import com.pms.clubmanagmentsystem.Entity.EventSponsors;

import java.util.List;

public class EventCreationRequest {

        private Event event;
        private List<Category> category;
        private List<EventSpeakers> speakers;
        private List<EventSponsors> sponsors;

    public List<EventSpeakers> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<EventSpeakers> speakers) {
        this.speakers = speakers;
    }

    public List<EventSponsors> getSponsors() {
        return sponsors;
    }

    public void setSponsors(List<EventSponsors> sponsors) {
        this.sponsors = sponsors;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }
}
