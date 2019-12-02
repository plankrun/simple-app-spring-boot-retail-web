package com.example.retailapplication.repository;

import com.example.retailapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class to access User Entity on database
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * select * from User where User.username = username and User.password
     * @param username username to be found in database
     * @param password password to be found in database
     * @return query result stored in User entity
     */
    User findByUsernameAndPassword(String username, String password);

    /**
     * select * from User where User.username = username
     * @param username username to be found in database
     * @return query result stored in User entity
     */
    User findByUsername(String username);
}
