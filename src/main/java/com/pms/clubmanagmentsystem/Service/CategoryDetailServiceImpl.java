package com.pms.clubmanagmentsystem.Service;

import com.pms.clubmanagmentsystem.Entity.Category;
import com.pms.clubmanagmentsystem.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryDetailServiceImpl {

    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }



}
