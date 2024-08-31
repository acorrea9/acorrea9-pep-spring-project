package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    /**
     * Checks to see if a specific username exists in the database
     * @param username a username.
     * @return true if the username exists in the database, false if otherwise
     */
    public boolean existsByUsername(String username);

    /**
     * Finds an account with a specific username and password
     * @param username a username.
     * @param password a password
     * @return an Optional, filled with an account if an account exists, empty if otherwise
     */
    public Optional<Account> findByUsernameAndPassword(String username, String password);
}
