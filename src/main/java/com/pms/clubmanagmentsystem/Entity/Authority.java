package com.pms.clubmanagmentsystem.Entity;



import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "Authority")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityID;

    private String authorityName;


    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Authority() {
    }

    public Authority(String authority) {
        this.authorityName = authority;
    }

    @Override
    public String getAuthority() {
        return null;
    }

    public void setAuthority(String authority) {
        this.authorityName = authority;
    }

    public Long getAuthorityID() {
        return authorityID;
    }

    public void setAuthorityID(Long authorityID) {
        this.authorityID = authorityID;
    }

}
