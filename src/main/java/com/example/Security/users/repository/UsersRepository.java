package com.example.Security.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Security.users.domain.Users;

public interface UsersRepository extends JpaRepository<Users,Long>{
    
    public Users findUserByEmail(String email);  

}
