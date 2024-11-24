package com.pms.clubmanagmentsystem.Controller;

import com.pms.clubmanagmentsystem.Entity.Club;
import com.pms.clubmanagmentsystem.Entity.ClubCategory;
import com.pms.clubmanagmentsystem.Entity.NotificationEvent;
import com.pms.clubmanagmentsystem.Entity.User;
import com.pms.clubmanagmentsystem.Service.ClubDetailServiceImpl;
import com.pms.clubmanagmentsystem.Service.EventDetailServiceImpl;
import com.pms.clubmanagmentsystem.dto.AdminNotificationResponse;
import com.pms.clubmanagmentsystem.dto.ClubCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/club")
public class ClubController {
    @Autowired
    private ClubDetailServiceImpl clubDetailService;


    @PostMapping("/createClub")
    public ResponseEntity<?> createClub(@AuthenticationPrincipal User authUser,
                                        @RequestBody ClubCreationRequest request) {
        try {
            String requestStatus=clubDetailService.creatNewClub(request, authUser);
            if (requestStatus.equals("true")) {
                return ResponseEntity.status(HttpStatus.OK).build();
            } else if (requestStatus.equals("nameExists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already has a club");
            }
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("User already has a club");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/editClub")
    public ResponseEntity<?> updateClub(@AuthenticationPrincipal User authUser,
                                        @RequestBody ClubCreationRequest request) {
        try {
            String resp= clubDetailService.updateClub(request,authUser);
            if (resp.equals("true")) {
                return ResponseEntity.status(HttpStatus.OK).build();
            } else if (resp.equals("nameExists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User already has a club");
            }
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/edit-contact-info")
    public ResponseEntity<?> updateClubContactInfo(@AuthenticationPrincipal User authUser,
                                                   @RequestBody Club club) {
        try {
            String resp= clubDetailService.updateClubContactInfo(authUser,club);
            if (resp.equals("true")) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getClub")
    public ResponseEntity<?> getClub(@AuthenticationPrincipal User authUser ) {
        try{
            Club club=clubDetailService.getClub(authUser);
        if(club!=null){
            return ResponseEntity.status(HttpStatus.OK).body(club);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO club was found");
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    }

    @GetMapping("/getAllClubs")
    public ResponseEntity<?> getAllClubs(@AuthenticationPrincipal User authUser ) {
        try{
            List<Club> clubs=clubDetailService.getAllClubs();
            if(!clubs.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(clubs);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO clubs was found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getAllClubsCategory")
    public ResponseEntity<?> getAllClubsCategory(@AuthenticationPrincipal User authUser ) {
        try{
            List<ClubCategory> clubs=clubDetailService.getAllClubsCategories();
            if(!clubs.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(clubs);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NO Categories was found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getClubNotifications/{id}")
    public ResponseEntity<?> getClubNotifications(@AuthenticationPrincipal User authUser ,
                                                  @PathVariable(value = "id") Long userId) {
        try{
            AdminNotificationResponse allNotifications= clubDetailService.getNotificationEvents(userId);

                return ResponseEntity.status(HttpStatus.OK).body(allNotifications);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/read-notification/{id}")
    public ResponseEntity<?> notificationToReadById(@AuthenticationPrincipal User authUser,
                                                    @PathVariable(value = "id") Long notificationID) {
        try {
            if (clubDetailService.notificationClubToRead(notificationID).equals("true")) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}

