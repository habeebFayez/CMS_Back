package com.pms.clubmanagmentsystem.Entity;

import javax.persistence.*;

@Entity
@Table(name = "ClubCategory")
public class ClubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubCategoryID;
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Long getClubCategoryID() {
        return clubCategoryID;
    }

    public void setClubCategoryID(Long clubCategoryID) {
        this.clubCategoryID = clubCategoryID;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
