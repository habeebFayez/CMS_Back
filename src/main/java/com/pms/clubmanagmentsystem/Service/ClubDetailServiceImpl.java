package com.pms.clubmanagmentsystem.Service;

import com.pms.clubmanagmentsystem.Entity.*;
import com.pms.clubmanagmentsystem.Repository.*;
import com.pms.clubmanagmentsystem.dto.AdminNotificationResponse;
import com.pms.clubmanagmentsystem.dto.ClubCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubDetailServiceImpl {


    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubCategoryRepository clubCategoryRepository;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private NotificationClubRepository notificationClubRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationEventRepository notificationEventRepository;
    private final Date currentDate =new Date(System.currentTimeMillis());
    private final LocalDate currentLocalDate = currentDate.toLocalDate();


    public String creatNewClub(ClubCreationRequest request, User authUser) {
        Club club = request.getClub();
        List<Category> category = request.getCategory();

        if (clubRepository.findByClubManager(authUser) != null) {
            return "false";
        }
        if (clubRepository.findByClubName(club.getClubName()) != null) {
            return "nameExists";
        }


        Club newClub = new Club();
        newClub.setClubName(club.getClubName());
        newClub.setClubDescription(club.getClubDescription());
        newClub.setClubManager(authUser);
        newClub.setClubCoverPicURL(club.getClubCoverPicURL());
        newClub.setClubProfilePicURL(club.getClubProfilePicURL());
        newClub.setClubMaxMembersNumber(100);
        newClub.setClubisActivation(false);
        newClub.setCreatingDate(new Date(System.currentTimeMillis()));
        clubRepository.save(newClub);
        for (Category category1 : category) {
            ClubCategory clubCategory = new ClubCategory();
            clubCategory.setCategory(category1);
            clubCategory.setClub(newClub);
            clubCategoryRepository.save(clubCategory);
        }
        if (authUser.getAuthority().getAuthorityName().equals("ROLE_ADMIN")) {

        }else{
            String Message="New Club Request Sent By \""+authUser.getEmail()+ " Student.";
            List<NotificationClub> notificationClub = new ArrayList<>();
            Authority authority = authorityRepository.getById(1L);
            List<User> Admins = userRepository.findAllByAuthority(authority);
            for (User admin : Admins) {
                NotificationClub notificationClub1 = new NotificationClub();
                notificationClub1.setClub(newClub);
                notificationClub1.setUser(admin);
                notificationClub1.setNotificationMessage(Message);
                notificationClub1.setCreationDate(currentDate);
                notificationClub1.setReadStatus(false);
                notificationClub1.setNotificationType("CREATE_CLUB");
                notificationClub.add(notificationClub1);
            }
            notificationClubRepository.saveAll(notificationClub);
        }
        return "true";

    }
    public String updateClub(ClubCreationRequest request, User authUser) {
        Club club = request.getClub();
        User user = userRepository.getById(request.getUserID());
        List<Category> category = request.getCategory();
        Club newClub = clubRepository.getById(club.getClubID());

        if (!newClub.getClubName().equals(club.getClubName())&&
                clubRepository.findByClubName(club.getClubName()) != null) {
            return "nameExists";
        }

        newClub.setClubName(club.getClubName());
        newClub.setClubDescription(club.getClubDescription());
        newClub.setClubCoverPicURL(club.getClubCoverPicURL());
        newClub.setClubisActivation(false);
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
        String Message="Update Club Request Sent By \""+authUser.getEmail()+ " Manager.";
        List<NotificationClub> notificationClub = new ArrayList<>();
        Authority authority = authorityRepository.getById(1L);
        List<User> Admins = userRepository.findAllByAuthority(authority);
        for (User admin : Admins) {
            NotificationClub notificationClub1 = new NotificationClub();
            notificationClub1.setClub(newClub);
            notificationClub1.setUser(admin);
            notificationClub1.setNotificationMessage(Message);
            notificationClub1.setCreationDate(currentDate);
            notificationClub1.setReadStatus(false);
            notificationClub1.setNotificationType("UPDATE_CLUB");
            notificationClub.add(notificationClub1);
        }
        notificationClubRepository.saveAll(notificationClub);


        return "true";

    }

    public String updateClubContactInfo( User authUser,Club club) {
        Club newClub = clubRepository.getById(club.getClubID());


        newClub.setContactEmail(club.getContactEmail());
        newClub.setContactNumber(club.getContactNumber());
        clubRepository.save(newClub);

        if(authUser!=null&&!(authUser.getAuthority().getAuthorityName().equals("ROLE_ADMIN"))) {
            String Message = newClub.getClubName() + " Club Contact Information's was Edited by the club Manager";
            List<NotificationClub> notificationClub = new ArrayList<>();
            Authority authority = authorityRepository.getById(1L);
            List<User> Admins = userRepository.findAllByAuthority(authority);
            for (User admin : Admins) {
                NotificationClub notificationClub1 = new NotificationClub();
                notificationClub1.setClub(newClub);
                notificationClub1.setUser(admin);
                notificationClub1.setNotificationMessage(Message);
                notificationClub1.setCreationDate(currentDate);
                notificationClub1.setReadStatus(false);
                notificationClub1.setNotificationType("UPDATE_CLUB");
                notificationClub.add(notificationClub1);
            }
            notificationClubRepository.saveAll(notificationClub);
        }

        return "true";

    }


    public Club getClub(User user) {

        return clubRepository.findByClubManager(user);

    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public List<ClubCategory> getAllClubsCategories() {
        List<ClubCategory> clubCategories = new ArrayList<>();
        for ( ClubCategory clubCategory : clubCategoryRepository.findAll()) {


                clubCategories.add(clubCategory);


        }


        return clubCategories;
    }


    public AdminNotificationResponse getNotificationEvents(Long userId) {
        AdminNotificationResponse notifications = new AdminNotificationResponse();
        Optional<User> user  = userRepository.findById(userId);
        if(user.isPresent()) {
            Club club  = clubRepository.findByClubManager(user.get());
            List<NotificationClub> notificationClubs = notificationClubRepository.findByUser(user.get());
            List<NotificationEvent> notificationEvents = notificationEventRepository.findByClub(club);


            notifications.setNotificationEvent(notificationEvents);
            notifications.setNotificationClub(notificationClubs);

            return notifications;
        }


        return null;
    }
    public String notificationClubToRead(Long notificationID) {
        NotificationClub notification = notificationClubRepository.getById(notificationID);
        notification.setReadStatus(true);
        notificationClubRepository.save(notification);
        return "true";

    }
    // change authority for manager when activating club by admin ################################
}

