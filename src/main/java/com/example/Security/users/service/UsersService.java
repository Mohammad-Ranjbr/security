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
        return usersRepository.findByEmail(email) ; 
    }

    public List<Users> findAllUsers(){
        return usersRepository.findAll();
    }

    public Users findUserById(Long id){
        Optional<Users> user = usersRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        return null ;
    }
    // authentication = It is the spring security variable that  
    // keeps the user information logged in the system
    @PreAuthorize("#users.email != authentication.name") // that the user cannot delete herself
    public void deleteUserById(Users users){
        usersRepository.deleteById(users.getId());
    }

    @Transactional
    public void addUser(Users users){ 
        usersRepository.saveAndFlush(users);
    }

}
