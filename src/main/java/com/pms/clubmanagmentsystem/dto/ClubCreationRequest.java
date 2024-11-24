package com.pms.clubmanagmentsystem.dto;

import com.pms.clubmanagmentsystem.Entity.Category;
import com.pms.clubmanagmentsystem.Entity.Club;
import com.pms.clubmanagmentsystem.Entity.User;

import java.util.List;

public class ClubCreationRequest {

        private Long userID;
        private Club club;
        private List<Category> category;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }
}
