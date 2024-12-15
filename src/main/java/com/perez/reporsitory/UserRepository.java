package com.perez.reporsitory;

import com.perez.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { //This is what type of entity the repository is aimed at.
    //METHODS   -----GRUD
    public User findByEmail(String username);  //find by email
}
