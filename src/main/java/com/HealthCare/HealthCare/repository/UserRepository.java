package com.HealthCare.HealthCare.repository;

import com.HealthCare.HealthCare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long>
{
    User findByUsername(String username);
    Boolean existsByUsername(String username);
    User findByEmail(String email);
    Boolean existsByEmail(String email);
}
