package com.pms.clubmanagmentsystem.Controller;

import com.pms.clubmanagmentsystem.Entity.User;
import com.pms.clubmanagmentsystem.Service.CategoryDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryDetailServiceImpl   categoryDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/getAllCategoreis")
    public ResponseEntity<?> getAllCategoreis(@AuthenticationPrincipal User authUser ) {
        try{
                return ResponseEntity.status(HttpStatus.OK).body(categoryDetailService.getAllCategories());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
