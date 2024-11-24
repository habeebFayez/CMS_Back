package com.pms.clubmanagmentsystem.Repository;

import com.pms.clubmanagmentsystem.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  CategoryRepository  extends JpaRepository<Category, Long> {

}
