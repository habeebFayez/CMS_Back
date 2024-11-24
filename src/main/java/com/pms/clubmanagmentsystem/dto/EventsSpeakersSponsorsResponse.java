package com.pms.clubmanagmentsystem.dto;

import com.pms.clubmanagmentsystem.Entity.EventSpeakers;
import com.pms.clubmanagmentsystem.Entity.EventSponsors;

import java.util.List;

public class EventsSpeakersSponsorsResponse {
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

}
