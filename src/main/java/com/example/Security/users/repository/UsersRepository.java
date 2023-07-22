package com.example.Security.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Security.users.domain.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long>{
    
    public Users findByEmail(String email);

}
