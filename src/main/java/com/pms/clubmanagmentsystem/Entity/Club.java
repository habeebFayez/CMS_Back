package com.pms.clubmanagmentsystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "clubs")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubID;
    @OneToOne(optional = false)
    @JoinColumn(name = "club_manager_user_id", unique = true)
    private User clubManager;
    @Column(unique = true , nullable = false)
    private String clubName;
    @Column(length = 5000)
    private String clubDescription;

    private boolean clubisActivation;
    private boolean clubisRejected;
    private boolean clubIsBlocked;
    private Date creatingDate;
    @Column(length = 10)
    private int clubRating;
    @Column(length = 10)
    private int clubMaxMembersNumber;
    @Column(length = 255)
    private int clubActiveEventsNumber;
    @Column(length = 255)
    private int clubRejectedEventsNumber;
    @Column(length = 500)
    private String clubCoverPicURL;
    @Column(length = 500)
    private String clubProfilePicURL;
    @Column(length = 255)
    private String contactEmail;
    @Column(length = 255)
    private String contactNumber;
//    @OneToMany(mappedBy = "club")
//    private List<ClubMembers> clubMemberships;

//    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
//    private List<ClubCategory> clubCategories;
////IMPORTANT NOTE:@JsonIgnore for get list becouse of JSON
//    @OneToMany(mappedBy = "club" , cascade = CascadeType.ALL)
//    private List<Event> events;

    public Long getClubID() {
        return clubID;
    }

    public void setClubID(Long clubID) {
        this.clubID = clubID;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public boolean getClubisActivation() {
        return clubisActivation;
    }

    public void setClubisActivation(boolean clubActivation) {
        this.clubisActivation = clubActivation;
    }

    public Date getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(Date creatingDate) {
        this.creatingDate = creatingDate;
    }

    public int getClubRating() {
        return clubRating;
    }

    public void setClubRating(int clubRating) {
        this.clubRating = clubRating;
    }

    public int getClubMaxMembersNumber() {
        return clubMaxMembersNumber;
    }

    public void setClubMaxMembersNumber(int clubMaxMembersNumber) {
        this.clubMaxMembersNumber = clubMaxMembersNumber;
    }

    public String getClubCoverPicURL() {
        return clubCoverPicURL;
    }

    public void setClubCoverPicURL(String clubCoverPicURL) {
        this.clubCoverPicURL = clubCoverPicURL;
    }

    public String getClubProfilePicURL() {
        return clubProfilePicURL;
    }

    public void setClubProfilePicURL(String clubProfilePicURL) {
        this.clubProfilePicURL = clubProfilePicURL;
    }

    public User getClubManager() {
        return clubManager;
    }

    public void setClubManager(User clubManager) {
        this.clubManager = clubManager;
    }

    public boolean isClubisActivation() {
        return clubisActivation;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getClubActiveEventsNumber() {
        return clubActiveEventsNumber;
    }

    public void setClubActiveEventsNumber(int clubActiveEventsNumber) {
        this.clubActiveEventsNumber = clubActiveEventsNumber;
    }

    public int getClubRejectedEventsNumber() {
        return clubRejectedEventsNumber;
    }

    public void setClubRejectedEventsNumber(int clubRejectedEventsNumber) {
        this.clubRejectedEventsNumber = clubRejectedEventsNumber;
    }

    public boolean isClubisRejected() {
        return clubisRejected;
    }

    public void setClubisRejected(boolean clubisRejected) {
        this.clubisRejected = clubisRejected;
    }

    public boolean isClubIsBlocked() {
        return clubIsBlocked;
    }

    public void setClubIsBlocked(boolean clubIsBlocked) {
        this.clubIsBlocked = clubIsBlocked;
    }
    //    @JsonIgnore
//    public List<ClubMembers> getClubMemberships() {
//        return clubMemberships;
//    }
//
//    public void setClubMemberships(List<ClubMembers> clubMemberships) {
//        this.clubMemberships = clubMemberships;
//    }
//    @JsonIgnore
//    public List<Event> getEvents() {
//        return events;
//    }
//
//    public void setEvents(List<Event> events) {
//        this.events = events;
//    }
//
//    @JsonIgnore
//    public List<ClubCategory> getClubCategories() {
//        return clubCategories;
//    }
//
//    public void setClubCategories(List<ClubCategory> clubCategories) {
//        this.clubCategories = clubCategories;
//    }
}
