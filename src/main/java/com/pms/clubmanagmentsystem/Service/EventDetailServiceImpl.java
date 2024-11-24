package com.pms.clubmanagmentsystem.Service;


import com.pms.clubmanagmentsystem.Entity.*;
import com.pms.clubmanagmentsystem.Repository.*;
import com.pms.clubmanagmentsystem.dto.EventCreationRequest;
import com.pms.clubmanagmentsystem.dto.EventsSpeakersSponsorsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventDetailServiceImpl {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventCategoryRepository eventCategoryRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private EventSpeakersRepository eventSpeakersRepository;
    @Autowired
    private EventSponsorsRepository eventSponsorsRepository;
    @Autowired
    private NotificationUserRepository notificationUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationEventRepository notificationEventRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    private final Date currentDate =new Date(System.currentTimeMillis());
    private final LocalDate currentLocalDate = currentDate.toLocalDate();



    public String creatNewEvent(EventCreationRequest request, User authUser) {
        Club club = clubRepository.findByClubManager(authUser);
        Event event = request.getEvent();
        List<Category> category = request.getCategory();
        List<EventSpeakers> speakers = request.getSpeakers();
        List<EventSponsors> sponsors = request.getSponsors();
        Event newEvent = new Event();
        String Message="New Event Request Sent By \""+club.getClubName()+ " Club.";
       List<NotificationUser> notificationUsers = new ArrayList<>();
       Authority authority = authorityRepository.getById(1L);
        List<User> Admins = userRepository.findAllByAuthority(authority);

        newEvent.setEventName(event.getEventName());
        newEvent.setEventDescription(event.getEventDescription());
        newEvent.setEventNote(event.getEventNote());
        newEvent.setEventEndTime(event.getEventEndTime());
        newEvent.setClub(club);
        newEvent.setEventCreationDate(currentDate);
        newEvent.setEventStartingDate(event.getEventStartingDate());
        newEvent.setEventLocationURL(event.getEventLocationURL());
        newEvent.setEventPostMediaURL(event.getEventPostMediaURL());
        newEvent.setEventLikes(0);
        newEvent.setEventStates(false);
        newEvent.setEventUpdated(false);
        event.setEventisRejected(false);
        newEvent.setEventHall(event.getEventHall());
        newEvent.setEventPostDescription(event.getEventPostDescription());
        newEvent.setEventPostRequested(event.isEventPostRequested());

        Event categoryEvent =eventRepository.save(newEvent);

        for (Category category1 : category) {
            EventCategory newEventCategory1 = new EventCategory();
            newEventCategory1.setEvent(categoryEvent);
            newEventCategory1.setCategory(category1);
            eventCategoryRepository.save(newEventCategory1);
        }
        for (EventSpeakers eventSpeakers : speakers) {
            EventSpeakers eventSpeakers1 = new EventSpeakers();
            eventSpeakers1.setEvent(categoryEvent);
            eventSpeakers1.setName(eventSpeakers.getName());
            eventSpeakers1.setContactNumber(eventSpeakers.getContactNumber());
            eventSpeakersRepository.save(eventSpeakers1);
        }
        for (EventSponsors eventSponsors : sponsors) {
            EventSponsors eventSponsors1 = new EventSponsors();
            eventSponsors1.setEvent(categoryEvent);
            eventSponsors1.setContactNumber(eventSponsors.getContactNumber());
            eventSponsors1.setName(eventSponsors.getName());
            eventSponsorsRepository.save(eventSponsors1);
        }

        toAdmin(Message,"EVENT_CREATION",categoryEvent, notificationUsers, Admins);

        return "true";

    }

    public List<Event> getAllEvents() {
        List<Event> activeEvents = new ArrayList<>();
        List<Event> allEvents = eventRepository.findAll();

        for (Event event : allEvents) {

            if(event.getClub().getClubisActivation()&&event.getEventStartingDate().after(Date.valueOf(currentLocalDate.minusDays(7)))){
                activeEvents.add(event);
            }

        }


        return activeEvents;
    }



    public boolean deletEeventById(Long eventId,User authUser) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();

            Club club = clubRepository.getById(event.getClub().getClubID());


            //delet category where event id is ?#######################
            List<EventCategory> eventCategory =eventCategoryRepository.findByEvent(event);
            eventCategoryRepository.deleteAll(eventCategory);

            List<EventSpeakers> eventSpeakers =eventSpeakersRepository.findByEvent(event);
            eventSpeakersRepository.deleteAll(eventSpeakers);


            List<EventSponsors>  eventSponsors =eventSponsorsRepository.findByEvent(event);
            eventSponsorsRepository.deleteAll(eventSponsors);

            //delete event where event id is ?#######################
            List<NotificationEvent>  notificationEvents =notificationEventRepository.findByEvent(event);
            notificationEventRepository.deleteAll(notificationEvents);
            List<NotificationUser>  notificationUsers2 =notificationUserRepository.findByEvent(event);
            notificationUserRepository.deleteAll(notificationUsers2);
            if(authUser!=null&&authUser.getAuthority().getAuthorityName().equals("ROLE_ADMIN")) {

                NotificationEvent notificationEvent = new NotificationEvent();
                String deletingMes="Event "+event.getEventName()+" Was Deleted By The Admin";
                notificationEvent.setClub(club);
                notificationEvent.setCreationDate(currentDate);
                notificationEvent.setNotificationMessage(deletingMes);
                notificationEvent.setReadStatus(false);
                notificationEvent.setNotificationType("EVENT_DELETING");
                notificationEventRepository.save(notificationEvent);

            }
            eventRepository.delete(event);
        }


        return true;
    }



    public String updateEventPost(EventCreationRequest request) {
        Event event = eventRepository.getById(request.getEvent().getEventID());
        Club club = clubRepository.getById(event.getClub().getClubID());
        List<NotificationUser> notificationUsers = new ArrayList<>();
        Authority authority = authorityRepository.getById(1L);
        List<User> admins = userRepository.findAllByAuthority(authority);
        String message= "Event "+event.getEventName()+" Post Was Updated By \""+club.getClubName()+ " Club.";

        event.setEventName(request.getEvent().getEventName());
        event.setEventNote(request.getEvent().getEventNote());
        event.setEventEndTime(request.getEvent().getEventEndTime());
        event.setEventStartingDate(request.getEvent().getEventStartingDate());
        event.setEventLocationURL(request.getEvent().getEventLocationURL());
        event.setEventPostMediaURL(request.getEvent().getEventPostMediaURL());
        event.setEventUpdated(true);
        event.setEventStates(false);
        event.setEventisRejected(false);
        event.setEventHall(request.getEvent().getEventHall());
        event.setEventPostDescription(request.getEvent().getEventPostDescription());
        eventRepository.save(event);
        List<NotificationUser>  notificationUsers2 =notificationUserRepository.findByEvent(event);
        notificationUserRepository.deleteAll(notificationUsers2);
        toAdmin(message,"EVENT_POST_UPDATE",event ,notificationUsers, admins);



        return "true";

    }
    public String updateAllEvent(EventCreationRequest request) {
        if(request.getEvent().getEventID()==null||
        request.getCategory()==null||
        request.getSpeakers()==null||
        request.getSponsors()==null){
            return "false";
        }

        long eeeeeee =request.getEvent().getEventID();
        Event event = eventRepository.getById(eeeeeee);
        List<Category> category = request.getCategory();
        List<EventSpeakers> speakers = request.getSpeakers();
        List<EventSponsors> sponsors = request.getSponsors();
        Club club = clubRepository.getById(event.getClub().getClubID());
        List<NotificationUser> notificationUsers = new ArrayList<>();
        Authority authority = authorityRepository.getById(1L);
        List<User> admins = userRepository.findAllByAuthority(authority);
        String message= "Event "+event.getEventName()+" Was Updated By \""+club.getClubName()+ " Club.";

        List<EventCategory> eventCate =eventCategoryRepository.findByEvent(event);
        eventCategoryRepository.deleteAll(eventCate);

        List<EventSpeakers> eventSpeak =eventSpeakersRepository.findByEvent(event);
        eventSpeakersRepository.deleteAll(eventSpeak);

        List<EventSponsors>  eventSpon =eventSponsorsRepository.findByEvent(event);
        eventSponsorsRepository.deleteAll(eventSpon);

        List<NotificationUser>  notificationUsers2 =notificationUserRepository.findByEvent(event);
        notificationUserRepository.deleteAll(notificationUsers2);

        event.setEventDescription(request.getEvent().getEventDescription());
        event.setEventName(request.getEvent().getEventName());
        event.setEventNote(request.getEvent().getEventNote());
        event.setEventPostRequested(request.getEvent().isEventPostRequested());
        event.setEventEndTime(request.getEvent().getEventEndTime());
        event.setEventStartingDate(request.getEvent().getEventStartingDate());
        event.setEventLocationURL(request.getEvent().getEventLocationURL());
        event.setEventPostMediaURL(request.getEvent().getEventPostMediaURL());
        event.setEventUpdated(true);
        event.setEventStates(false);
        event.setEventisRejected(false);
        event.setEventHall(request.getEvent().getEventHall());
        event.setEventPostDescription(request.getEvent().getEventPostDescription());
        eventRepository.save(event);


        for (Category category1 : category) {
            EventCategory newEventCategory1 = new EventCategory();
            newEventCategory1.setEvent(event);
            newEventCategory1.setCategory(category1);
            eventCategoryRepository.save(newEventCategory1);
        }

        for (EventSpeakers eventSpeakers : speakers) {
            EventSpeakers eventSpeakers1 = new EventSpeakers();
            eventSpeakers1.setEvent(event);
            eventSpeakers1.setName(eventSpeakers.getName());
            eventSpeakers1.setContactNumber(eventSpeakers.getContactNumber());
            eventSpeakersRepository.save(eventSpeakers1);
        }

        for (EventSponsors eventSponsors : sponsors) {
            EventSponsors eventSponsors1 = new EventSponsors();
            eventSponsors1.setEvent(event);
            eventSponsors1.setContactNumber(eventSponsors.getContactNumber());
            eventSponsors1.setName(eventSponsors.getName());
            eventSponsorsRepository.save(eventSponsors1);
        }
        toAdmin(message,"EVENT_UPDATE",event ,notificationUsers, admins);
        return "true";

    }
    public List<EventCategory> getAllEventsCategories() {
        List<EventCategory> eventCategories = new ArrayList<>();
        for (EventCategory eventCategory : eventCategoryRepository.findAll()) {

            if( eventCategory.getEvent().getEventStartingDate().after(Date.valueOf(currentLocalDate.minusDays(5)))){
                eventCategories.add(eventCategory);
            }

        }


        return eventCategories;
    }
    public EventsSpeakersSponsorsResponse getAllEventSpeakersSponsors( Long eventId) {
        EventsSpeakersSponsorsResponse eventsSpeakersSponsorsResponse = new EventsSpeakersSponsorsResponse();
        List<EventSpeakers> speakers=new ArrayList<>();
         List<EventSponsors> sponsors=new ArrayList<>();


        for (EventSpeakers eventSpeakers : eventSpeakersRepository.findAll()){

            if( eventSpeakers.getEvent().getEventID().equals(eventId)){
                speakers.add(eventSpeakers);
            }

        }
        for (EventSponsors eventSponsors : eventSponsorsRepository.findAll()){

            if( eventSponsors.getEvent().getEventID().equals(eventId)){
                sponsors.add(eventSponsors);
            }

        }
        eventsSpeakersSponsorsResponse.setSpeakers(speakers);
        eventsSpeakersSponsorsResponse.setSponsors(sponsors);

        return eventsSpeakersSponsorsResponse;
    }


    public String notificationToRead(Long notificationID) {
    NotificationEvent notification = notificationEventRepository.getById(notificationID);
    notification.setReadStatus(true);
    notificationEventRepository.save(notification);
        return "true";

    }

    private void toAdmin(String message,String notificationType ,Event event,List<NotificationUser> notificationUsers, List<User> admins) {
        for (User admin : admins) {
            NotificationUser notificationUser = new NotificationUser();
            notificationUser.setUser(admin);
            notificationUser.setEvent(event);
            notificationUser.setCreationDate(currentDate);
            notificationUser.setNotificationMessage(message);
            notificationUser.setReadStatus(false);
            notificationUser.setNotificationType(notificationType);

            notificationUsers.add(notificationUser);
        }


        notificationUserRepository.saveAll(notificationUsers);
    }
}
