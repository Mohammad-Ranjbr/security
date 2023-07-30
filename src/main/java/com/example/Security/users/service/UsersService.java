package com.example.Security.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Security.users.domain.Users;
import com.example.Security.users.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService implements UserDetailsService{

    @Autowired
    private UsersRepository usersRepository ;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usersRepository.findUserByEmail(email);
    } 

    public List<Users> findAllUsers(){
        return  usersRepository.findAll();
    }

    public Users findUserById(Long id){
        Optional<Users> user = usersRepository.findById(id);
        if(user.isPresent()){ 
            return user.get(); 
        }
        return null ;
    }

    @Transactional
    public void addUSer(Users user){
        usersRepository.saveAndFlush(user);
    }

    /*@Transactional
    public void deleteUserById(Long id){
        usersRepository.deleteById(id);
    }*/

    @Transactional
    @PreAuthorize("#user.email != authentication.name") 
    public void deleteUserById(Users user){
        usersRepository.deleteById(user.getId());
    } 
    
}
