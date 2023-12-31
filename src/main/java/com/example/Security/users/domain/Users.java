package com.example.Security.users.domain;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import com.example.Security.enums.UserRoles;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection; 
import java.util.List;  
import java.util.ArrayList;  

@Entity
public class Users implements UserDetails{  
    
    // Instance Variable

    @Id
    @GeneratedValue
    private Long id ;

    private String email ; 
    private String password ;
 
    private Boolean enabled = true ;

    // @ElementCollection(targetClass = UserRoles.class , fetch = FetchType.EAGER)    
    // @CollectionTable(name = "authorities" , joinColumns = @JoinColumn(name="email" , referencedColumnName = "email")) 
    // @Enumerated(EnumType.STRING) 
    // private List<UserRoles> userRoles ;

    @ManyToMany(fetch = FetchType.EAGER) 
    private List<Roles> roles;

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
    // public void setUserRoles(List<UserRoles> userRoles) {
    //     this.userRoles = userRoles;
    // }
    public void setRoles(List<Roles> roles) {
        this.roles = roles;
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
    // public List<UserRoles> getUserRoles() {
    //     return userRoles;
    // }
    public List<Roles> getRoles() {
        return roles;
    }

    // UserDetails methods

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return userRoles ;
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Roles roles : roles)
            authorities.addAll(roles.getAuthorities());
        return authorities;      
    }
    @Override
    public String getUsername() {
        return email ;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true ;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true ;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    } 
    @Override
    public boolean isEnabled() {
        return enabled ;
    }

}
