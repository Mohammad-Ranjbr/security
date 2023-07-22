package com.example.Security.users.domain;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import com.example.Security.enums.UserRoles;

import java.util.List;
import java.io.Serializable;  

@Entity
public class Users implements Serializable{ 
    
    // Instance Variable

    @Id
    @GeneratedValue
    private Long id ;

    private String email ;
    private String password ;

    private Boolean enabled = true ;

    @ElementCollection(targetClass = UserRoles.class)
    @CollectionTable(name = "authorities" , joinColumns = @JoinColumn(name="email" , referencedColumnName = "email")) 
    @Enumerated(EnumType.STRING) 
    private List<UserRoles> userRoles ;

    // Constructors 

    public Users(){}
    public Users(String email , String password){
        this.email = email ;
        this.password = password ;
    }

    // Setters 

    public void setId(Long id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public void setUserRoles(List<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }

    // Getters 

    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

}
