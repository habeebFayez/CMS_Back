package com.pms.clubmanagmentsystem.Controller;

import com.pms.clubmanagmentsystem.Entity.*;
import com.pms.clubmanagmentsystem.Repository.EventCategoryRepository;
import com.pms.clubmanagmentsystem.Repository.EventRepository;
import com.pms.clubmanagmentsystem.Service.EventDetailServiceImpl;
import com.pms.clubmanagmentsystem.dto.EventCreationRequest;
import com.pms.clubmanagmentsystem.dto.EventsSpeakersSponsorsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private EventDetailServiceImpl eventDetailService;
    @Autowired
    private EventCategoryRepository eventCategoryRepository;
    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/createEvent")
    public ResponseEntity<?> createEvent(@AuthenticationPrincipal User authUser,
                                        @RequestBody EventCreationRequest request) {
        try {
            if (eventDetailService.creatNewEvent(request, authUser).equals("true")) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getallevents")
    public ResponseEntity<?> getAllEvents(@AuthenticationPrincipal User authUser) {
        try {
            List<Event> events = eventDetailService.getAllEvents();
            return ResponseEntity.status(HttpStatus.OK).body(events);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteevent/{id}")
    public ResponseEntity<?> deleteEvent(@AuthenticationPrincipal User authUser ,
                                         @PathVariable(value = "id") Long eventId) {
        try {
          eventDetailService.deletEeventById(eventId,authUser);
                return ResponseEntity.status(HttpStatus.OK).body("Event was deleted successfully!!");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/editEventpost")
    public ResponseEntity<?> editEvent(@AuthenticationPrincipal User authUser,
                                        @RequestBody EventCreationRequest request) {
        try {
            if (eventDetailService.updateEventPost(request).equals("true")) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/edit-full-event")
    public ResponseEntity<?> editAllEvent(@AuthenticationPrincipal User authUser,
                                       @RequestBody EventCreationRequest request) {
        try {
            if (eventDetailService.updateAllEvent(request).equals("true")) {


                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
    @GetMapping("/getalleventscategories")
    public ResponseEntity<?> getAllEventsCategories(@AuthenticationPrincipal User authUser) {
        try {
            List<EventCategory> eventCategories = eventDetailService.getAllEventsCategories();
            return ResponseEntity.status(HttpStatus.OK).body(eventCategories);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getallevent-speakers-sponsors/{id}")
    public ResponseEntity<?> getAllEventSpeakersSponsors(@AuthenticationPrincipal User authUser,
                                                          @PathVariable(value = "id") Long eventId) {
        try {
            EventsSpeakersSponsorsResponse eventsSpeakersSponsorsResponse
                    = eventDetailService.getAllEventSpeakersSponsors(eventId);
            return ResponseEntity.status(HttpStatus.OK).body(eventsSpeakersSponsorsResponse);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/read-notification/{id}")
    public ResponseEntity<?> notificationToReadById(@AuthenticationPrincipal User authUser,
                                          @PathVariable(value = "id") Long notificationID) {
        try {
            if (eventDetailService.notificationToRead(notificationID).equals("true")) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

}