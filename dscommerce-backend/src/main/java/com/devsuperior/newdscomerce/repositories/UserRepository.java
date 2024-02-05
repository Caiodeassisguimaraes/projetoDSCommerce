package com.devsuperior.newdscomerce.repositories;

import com.devsuperior.newdscomerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
