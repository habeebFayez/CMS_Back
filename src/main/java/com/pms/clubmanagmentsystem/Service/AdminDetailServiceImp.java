package com.pms.clubmanagmentsystem.Service;

import com.pms.clubmanagmentsystem.Entity.*;
import com.pms.clubmanagmentsystem.Repository.*;
import com.pms.clubmanagmentsystem.dto.AdminNotificationResponse;
import com.pms.clubmanagmentsystem.dto.AdminRequests;
import com.pms.clubmanagmentsystem.dto.ClubCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminDetailServiceImp {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventCategoryRepository eventCategoryRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationEventRepository notificationEventRepository;
    @Autowired
    private ClubMembersRepository clubMembersRepository;
    @Autowired
    private NotificationUserRepository notificationUserRepository;
    @Autowired
    private NotificationClubRepository notificationClubRepository;
    @Autowired
    private ClubCategoryRepository clubCategoryRepository;
    @Autowired
    private EventDetailServiceImpl eventDetailService;

    private final Date currentDate =new Date(System.currentTimeMillis());
    private final LocalDate currentLocalDate = currentDate.toLocalDate();
    private final Long ROLE_ADMIN = 1L;
    private final Long ROLE_STUDENT = 2L;
    private final Long ROLE_MANAGER = 3L;



    public String creatNewClub(ClubCreationRequest request, User authUser) {
        Club club = request.getClub();
        User user = userRepository.getById(request.getUserID());
        String ActivateMes="Admin has Appointed You as a Club Manager For \""+club.getClubName()+"\" Club";
        NotificationClub notificationClub = new NotificationClub();
        List<Category> category = request.getCategory();

        if (clubRepository.findByClubManager(user) != null||user==null) {
            return "false";
        }
        if (clubRepository.findByClubName(club.getClubName()) != null) {
            return "nameExists";
        }


        Club newClub = new Club();
        newClub.setClubName(club.getClubName());
        newClub.setClubDescription(club.getClubDescription());
        newClub.setClubManager(user);
        newClub.setClubCoverPicURL(club.getClubCoverPicURL());
        newClub.setClubProfilePicURL(club.getClubProfilePicURL());
        newClub.setClubMaxMembersNumber(100);
        newClub.setClubisActivation(true);
        newClub.setCreatingDate(currentDate);
        clubRepository.save(newClub);

        for (Category category1 : category) {
            ClubCategory clubCategory = new ClubCategory();
            clubCategory.setCategory(category1);
            clubCategory.setClub(newClub);
            clubCategoryRepository.save(clubCategory);
        }

        user.setAuthority((authorityRepository.getById(ROLE_MANAGER)));
        userRepository.save(user);

        notificationClub.setUser(user);
        notificationClub.setClub(newClub);
        notificationClub.setCreationDate(currentDate);
        notificationClub.setNotificationMessage(ActivateMes);
        notificationClub.setReadStatus(false);
        notificationClub.setNotificationType("CLUB_ASSIGNING");


        notificationClubRepository.save(notificationClub);

        return "true";

    }

    public String updateClub(ClubCreationRequest request, User authUser) {
        Club club = request.getClub();
        User user = userRepository.getById(request.getUserID());

        String ActivateMes="Admin has Appointed You as the Club Manager For \""+club.getClubName()+"\" Club";
        NotificationClub notificationClub = new NotificationClub();
        List<Category> category = request.getCategory();
        Club newClub = clubRepository.getById(club.getClubID());
        Long existedManager=newClub.getClubManager().getUserID();

        if (existedManager!= user.getUserID()&&
        clubRepository.findByClubManager(user) != null||user==null) {
            return "false";
        }
        if (!newClub.getClubName().equals(club.getClubName())&&
                clubRepository.findByClubName(club.getClubName()) != null) {
            return "nameExists";
        }

        if (existedManager!= user.getUserID()){
            User oldUser= userRepository.getById(newClub.getClubManager().getUserID());
            oldUser.setAuthority((authorityRepository.getById(ROLE_STUDENT)));
            userRepository.save(user);
        }

        newClub.setClubName(club.getClubName());
        newClub.setClubDescription(club.getClubDescription());
        newClub.setClubManager(user);
        newClub.setClubisActivation(true);
        newClub.setClubIsBlocked(false);
        newClub.setClubisRejected(false);
        newClub.setClubCoverPicURL(club.getClubCoverPicURL());
        newClub.setClubProfilePicURL(club.getClubProfilePicURL());
        clubRepository.save(newClub);

        List<ClubCategory> clubCategories =clubCategoryRepository.findByClub(newClub);
        clubCategoryRepository.deleteAll(clubCategories);
        for (Category category1 : category) {
            ClubCategory clubCategory = new ClubCategory();
            clubCategory.setCategory(category1);
            clubCategory.setClub(newClub);
            clubCategoryRepository.save(clubCategory);
        }
        if (existedManager!= user.getUserID()){
            user.setAuthority((authorityRepository.getById(ROLE_MANAGER)));
            userRepository.save(user);

            notificationClub.setUser(user);
            notificationClub.setClub(newClub);
            notificationClub.setCreationDate(currentDate);
            notificationClub.setNotificationMessage(ActivateMes);
            notificationClub.setReadStatus(false);
            notificationClub.setNotificationType("CLUB_ASSIGNING");


            notificationClubRepository.save(notificationClub);
        }else if(existedManager == user.getUserID()){
            notificationClub.setUser(user);
            notificationClub.setClub(newClub);
            notificationClub.setCreationDate(currentDate);
            notificationClub.setNotificationMessage(
                    "Club Data was Edited By admin for \""+club.getClubName()+"\" Club");
            notificationClub.setReadStatus(false);
            notificationClub.setNotificationType("CLUB_UPDATE");
            List<NotificationClub> notificationClubToDelete =notificationClubRepository.findByUser(user);
            notificationClubRepository.deleteAll(notificationClubToDelete);

            notificationClubRepository.save(notificationClub);

        }


        return "true";

    }
    public String deactivateClub(AdminRequests request) {
        Club club = clubRepository.getById(request.getClubID());
        User user = userRepository.getById(club.getClubManager().getUserID());
        if(club == null){
            return "false";
        }
        String deactivateEventReason=request.getNotification().getNotificationMessage();
        NotificationClub notificationClub = new NotificationClub();


        club.setClubisActivation(false);
        club.setClubIsBlocked(true);
        club.setClubisRejected(false);
        clubRepository.save(club);

        notificationClub.setUser(user);
        notificationClub.setClub(club);
        notificationClub.setCreationDate(currentDate);
        notificationClub.setNotificationMessage("Your Club "+club.getClubName()+" has been deactivated By Admin \n"+
                "Reason : \n"+(!deactivateEventReason.isEmpty()?deactivateEventReason
                :"Not Specified!!" ));
        notificationClub.setReadStatus(false);
        notificationClub.setNotificationType("CLUB_DEACTIVATION");

        List<NotificationClub> notificationClubToDelete =notificationClubRepository.findByUser(user);
        notificationClubRepository.deleteAll(notificationClubToDelete);

        notificationClubRepository.save(notificationClub);




        return "true";

    }
    public String acceptClub(Long clubId) {
        Club club = clubRepository.getById(clubId);
        User user = userRepository.getById(club.getClubManager().getUserID());
        if(club == null){
            return "false";
        }
        String ActivateMes="Your Club Request For \""+club.getClubName()+"\" Club has been Accepted";
        NotificationClub notificationClub = new NotificationClub();

        club.setClubisActivation(true);
        club.setClubIsBlocked(false);
        club.setClubisRejected(false);
        clubRepository.save(club);

        user.setAuthority((authorityRepository.getById(ROLE_MANAGER)));
        userRepository.save(user);
        notificationClub.setUser(user);
        notificationClub.setClub(club);
        notificationClub.setCreationDate(currentDate);
        notificationClub.setNotificationMessage(ActivateMes);
        notificationClub.setReadStatus(false);
        notificationClub.setNotificationType("CLUB_ACTIVATION");
        List<NotificationClub> notificationClubToDelete =notificationClubRepository.findByUser(user);
        notificationClubRepository.deleteAll(notificationClubToDelete);

        notificationClubRepository.save(notificationClub);


        return "true";

    }
    public String rejectClub(AdminRequests request) {
        Club club = clubRepository.getById(request.getClubID());
        User user = userRepository.getById(club.getClubManager().getUserID());
        if(club == null){
            return "false";
        }
        String deactivateEventReason=request.getNotification().getNotificationMessage();
        NotificationClub notificationClub = new NotificationClub();


        club.setClubisActivation(false);
        club.setClubIsBlocked(false);
        club.setClubisRejected(true);
        clubRepository.save(club);

        notificationClub.setUser(user);
        notificationClub.setClub(club);
        notificationClub.setCreationDate(currentDate);
        notificationClub.setNotificationMessage("Your Club Request "+club.getClubName()+" has been Rejected By Admin\n"+
                "Reason : \n"+(!deactivateEventReason.isEmpty()?deactivateEventReason
                :"Not Specified!!" ));
        notificationClub.setReadStatus(false);
        notificationClub.setNotificationType("CLUB_REJECTION");

        List<NotificationClub> notificationClubToDelete =notificationClubRepository.findByClub(club);
        notificationClubRepository.deleteAll(notificationClubToDelete);

        notificationClubRepository.save(notificationClub);



        return "true";

    }
    public String deleteClub(Long clubId) {
        Club club = clubRepository.getById(clubId);
        User user = userRepository.getById(club.getClubManager().getUserID());
        if (club==null ) {
            return "false";
        }


           //delete where club id is ?#######################
            List<ClubCategory> clubCategories =clubCategoryRepository.findByClub(club);
             clubCategoryRepository.deleteAll(clubCategories);

            List<NotificationEvent>  notificationEvents =notificationEventRepository.findByClub(club);
            notificationEventRepository.deleteAll(notificationEvents);

            List<NotificationClub>  notificationClubs =notificationClubRepository.findByClub(club);
            notificationClubRepository.deleteAll(notificationClubs);


            List<ClubMembers>  members =clubMembersRepository.findByClub(club);
            clubMembersRepository.deleteAll(members);

            List<Event>  events =eventRepository.findByClub(club);
            for(Event event : events){
                List<NotificationUser>  notificationUsers =notificationUserRepository.findByEvent(event);
                notificationUserRepository.deleteAll(notificationUsers);
                eventDetailService.deletEeventById(event.getEventID(),null);
            }


            user.setAuthority((authorityRepository.getById(ROLE_STUDENT)));
            userRepository.save(user);

            clubRepository.delete(club);



        return "true";

    }

    public String deactivateEvent(AdminRequests request) {
        Event event = eventRepository.getById(request.getEventID());
        Club club = clubRepository.getById(event.getClub().getClubID());
        String deactivateEventReason=request.getNotification().getNotificationMessage();
        NotificationEvent notificationEvent = new NotificationEvent();

        event.setEventUpdated(false);
        event.setEventStates(false);
        event.setEventisRejected(false);
        eventRepository.save(event);
        club.setClubActiveEventsNumber(club.getClubActiveEventsNumber()-1);
        clubRepository.save(club);

        notificationEvent.setClub(club);
        notificationEvent.setEvent(event);
        notificationEvent.setCreationDate(currentDate);
        notificationEvent.setNotificationMessage("Your event "+event.getEventName()+" has been deactivated By Admin \n"+
                                            "Reason : \n"+(!deactivateEventReason.isEmpty()?deactivateEventReason
                                            :"Not Specified!!" ));
        notificationEvent.setReadStatus(false);
        notificationEvent.setNotificationType("EVENT_DEACTIVATION");
        List<NotificationEvent>  notificationEvents =notificationEventRepository.findByEvent(event);
        notificationEventRepository.deleteAll(notificationEvents);
        List<NotificationUser>  notificationUsers =notificationUserRepository.findByEvent(event);
        notificationUserRepository.deleteAll(notificationUsers);

        notificationEventRepository.save(notificationEvent);


        return "true";

    }
    public String acceptEvent(Long eventId) {
        Event event = eventRepository.getById(eventId);
        Club club = clubRepository.getById(event.getClub().getClubID());
        String ActivateMes="Your Event Request For \""+event.getEventName()+"\" Event has been Accepted";
        NotificationEvent notificationEvent = new NotificationEvent();

        event.setEventUpdated(false);
        event.setEventStates(true);
        event.setEventisRejected(false);
        eventRepository.save(event);
        club.setClubActiveEventsNumber(club.getClubActiveEventsNumber()+1);
        clubRepository.save(club);
        notificationEvent.setEvent(event);
        notificationEvent.setClub(club);
        notificationEvent.setCreationDate(currentDate);
        notificationEvent.setNotificationMessage(ActivateMes);
        notificationEvent.setReadStatus(false);
        notificationEvent.setNotificationType("EVENT_ACTIVATION");
        List<NotificationEvent>  notificationEvents =notificationEventRepository.findByEvent(event);
        notificationEventRepository.deleteAll(notificationEvents);
        List<NotificationUser>  notificationUsers =notificationUserRepository.findByEvent(event);
        notificationUserRepository.deleteAll(notificationUsers);
        notificationEventRepository.save(notificationEvent);


        return "true";

    }
    public String rejectEvent(AdminRequests request) {
        Event event = eventRepository.getById(request.getEventID());
        Club club = clubRepository.getById(event.getClub().getClubID());
        String deactivateEventReason=request.getNotification().getNotificationMessage();
        String rejectingMes="Your Event Request For \""+event.getEventName()+"\" Event has been Rejected By Admin!!"
                +"\nReason : \n"+(!deactivateEventReason.isEmpty()?deactivateEventReason:"Not Specified!!");
        NotificationEvent notificationEvent = new NotificationEvent();

        event.setEventUpdated(false);
        event.setEventStates(false);
        event.setEventisRejected(true);
        eventRepository.save(event);
        club.setClubRejectedEventsNumber(club.getClubRejectedEventsNumber()+1);
        clubRepository.save(club);
        notificationEvent.setEvent(event);
        notificationEvent.setClub(club);
        notificationEvent.setCreationDate(currentDate);
        notificationEvent.setNotificationMessage(rejectingMes);
        notificationEvent.setReadStatus(false);
        notificationEvent.setNotificationType("EVENT_REJECTION");
        List<NotificationEvent>  notificationEvents =notificationEventRepository.findByEvent(event);
        notificationEventRepository.deleteAll(notificationEvents);
        List<NotificationUser>  notificationUsers =notificationUserRepository.findByEvent(event);
        notificationUserRepository.deleteAll(notificationUsers);

        notificationEventRepository.save(notificationEvent);


        return "true";

    }
    public AdminNotificationResponse getNotifications(Long adminID) {
        AdminNotificationResponse notifications = new AdminNotificationResponse();
        Optional<User> admin  = userRepository.findById(adminID);
        if(admin.isPresent()) {
            List<NotificationUser> notificationUsers = notificationUserRepository.findByUser(admin.get());
            List<NotificationClub> notificationClubs = notificationClubRepository.findByUser(admin.get());

            notifications.setNotificationUser(notificationUsers);
            notifications.setNotificationClub(notificationClubs);

            return notifications;

        }

        return null;
    }

    public String notificationUserToRead(Long notificationID) {
        NotificationUser notification = notificationUserRepository.getById(notificationID);
        notification.setReadStatus(true);
        notificationUserRepository.save(notification);
        return "true";

    }
//    public String notificationClubToRead(Long notificationID) {
//        NotificationClub notification = notificationClubRepository.getById(notificationID);
//        notification.setReadStatus(true);
//        notificationClubRepository.save(notification);
//        return "true";
//
//    }
}
