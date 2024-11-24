package com.pms.clubmanagmentsystem.Controller;


import com.pms.clubmanagmentsystem.Entity.Event;
import com.pms.clubmanagmentsystem.Entity.NotificationEvent;
import com.pms.clubmanagmentsystem.Entity.User;
import com.pms.clubmanagmentsystem.Service.AdminDetailServiceImp;
import com.pms.clubmanagmentsystem.Service.ClubDetailServiceImpl;
import com.pms.clubmanagmentsystem.Service.EventDetailServiceImpl;
import com.pms.clubmanagmentsystem.Service.UserDetailServiceImpl;
import com.pms.clubmanagmentsystem.dto.AdminNotificationResponse;
import com.pms.clubmanagmentsystem.dto.AdminRequests;
import com.pms.clubmanagmentsystem.dto.ClubCreationRequest;
import com.pms.clubmanagmentsystem.dto.EventCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private EventDetailServiceImpl eventDetailService;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private ClubDetailServiceImpl clubDetailService;
    @Autowired
    private AdminDetailServiceImp adminDetailService;



    @PostMapping("/createClub")
    public ResponseEntity<?> createClub(@AuthenticationPrincipal User authUser,
                                        @RequestBody ClubCreationRequest request) {
        try {
            String requestStatus=adminDetailService.creatNewClub(request, authUser);
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
            String resp= adminDetailService.updateClub(request,authUser);
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

    @GetMapping("/getUsers")
    public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal User authUser) {
        try {
            if (authUser == null || !authUser.getAuthority().getAuthorityName().equals("ROLE_ADMIN") ){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            List<User> users = userDetailService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/deactivateEvent")
    public ResponseEntity<?> deactivateEvent(@AuthenticationPrincipal User authUser,
                                             @RequestBody AdminRequests request) {
        try {
            if (adminDetailService.deactivateEvent(request).equals("true")) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/activateEvent/{id}")
    public ResponseEntity<?> activateEvent(@AuthenticationPrincipal User authUser,
                                           @PathVariable(value = "id") Long eventId) {
        try {
            adminDetailService.acceptEvent(eventId);
            return ResponseEntity.status(HttpStatus.OK).body("Event was Accepted successfully!!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/rejectEvent")
    public ResponseEntity<?> rejectEvent(@AuthenticationPrincipal User authUser,
                                         @RequestBody AdminRequests request) {
        try {
            adminDetailService.rejectEvent(request);
            return ResponseEntity.status(HttpStatus.OK).body("Event was Accepted successfully!!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/deactivateClub")
    public ResponseEntity<?> deactivateClub(@AuthenticationPrincipal User authUser,
                                             @RequestBody AdminRequests request) {
        try {
            if (adminDetailService.deactivateClub(request).equals("true")) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/activateClub/{id}")
    public ResponseEntity<?> activateClub(@AuthenticationPrincipal User authUser,
                                           @PathVariable(value = "id") Long clubId) {
        try {
          if(  adminDetailService.acceptClub(clubId).equals("true")) {
              return ResponseEntity.status(HttpStatus.OK).body("Event was Accepted successfully!!");
          }
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @PutMapping("/rejectClub")
    public ResponseEntity<?> rejectClub(@AuthenticationPrincipal User authUser,
                                         @RequestBody AdminRequests request) {
        try {
            adminDetailService.rejectClub(request);
            return ResponseEntity.status(HttpStatus.OK).body("Event was Accepted successfully!!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @DeleteMapping("/deleteClub/{id}")
    public ResponseEntity<?> deleteClub(@AuthenticationPrincipal User authUser,
                                        @PathVariable(value = "id") Long clubId){
        try {
            if (adminDetailService.deleteClub(clubId).equals("true")) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @GetMapping("/getAdminNotifications/{id}")
    public ResponseEntity<?> getClubNotifications(@AuthenticationPrincipal User authUser ,
                                                  @PathVariable(value = "id") Long adminID) {
        try{
            AdminNotificationResponse allNotifications= adminDetailService.getNotifications(adminID);

            return ResponseEntity.status(HttpStatus.OK).body(allNotifications);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    @PutMapping("/read-notification-club/{id}")
//    public ResponseEntity<?> notificationClubToReadById(@AuthenticationPrincipal User authUser,
//                                                    @PathVariable(value = "id") Long notificationID) {
//        try {
//            if (adminDetailService.notificationClubToRead(notificationID).equals("true")) {
//                return ResponseEntity.status(HttpStatus.OK).build();
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
//        }
//    }
    @PutMapping("/read-notification-user/{id}")
    public ResponseEntity<?> notificationUserToReadById(@AuthenticationPrincipal User authUser,
                                                    @PathVariable(value = "id") Long notificationID) {
        try {
            if (adminDetailService.notificationUserToRead(notificationID).equals("true")) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}
