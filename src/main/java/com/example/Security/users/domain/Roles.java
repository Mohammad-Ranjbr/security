package com.example.Security.users.domain;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.Security.enums.Authority;


import java.util.List;

@Entity
public class Roles {
    
    // Instance Variable

    @Id
    @GeneratedValue
    private Long id ;

    private String name ;

    @ElementCollection(targetClass = Authority.class , fetch = FetchType.EAGER)  
    private List<Authority> authorities ;

    // Setters 

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    } 

    // Getters 

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Authority> getAuthorities() {
        return authorities;
    }

}
