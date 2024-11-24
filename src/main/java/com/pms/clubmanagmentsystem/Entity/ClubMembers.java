package com.pms.clubmanagmentsystem.Entity;

import javax.persistence.*;

@Entity
@Table(name = "ClubsMembership")
public class ClubMembers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubMembersID;
    // many .... many relationships to club and user so ID's are needed
    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Long getClubMembersID() {
        return clubMembersID;
    }

    public void setClubMembersID(Long clubMembersID) {
        this.clubMembersID = clubMembersID;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
