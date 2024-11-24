package com.pms.clubmanagmentsystem.Repository;

import com.pms.clubmanagmentsystem.Entity.Club;
import com.pms.clubmanagmentsystem.Entity.ClubMembers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubMembersRepository  extends JpaRepository<ClubMembers, Long> {

    List<ClubMembers> findByClub(Club club);
}
