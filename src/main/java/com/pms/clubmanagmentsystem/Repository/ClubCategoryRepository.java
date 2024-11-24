package com.pms.clubmanagmentsystem.Repository;

import com.pms.clubmanagmentsystem.Entity.Club;
import com.pms.clubmanagmentsystem.Entity.ClubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubCategoryRepository  extends JpaRepository<ClubCategory, Long> {

    List<ClubCategory> findByClub(Club club);
}
