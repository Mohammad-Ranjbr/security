package com.example.Security.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Security.users.repository.UsersRepository;

@Service
public class UsersService implements UserDetailsService{
    
    private final UsersRepository usersRepository ;

    @Autowired
    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository ; 
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usersRepository.findByEmail(email) ; 
    }

}
