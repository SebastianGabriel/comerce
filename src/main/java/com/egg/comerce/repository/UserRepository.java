package com.egg.comerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.egg.comerce.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
