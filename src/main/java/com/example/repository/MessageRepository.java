package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    /**
     * Finds all of the messages posted by a certain account id
     * @param postedBy an account id.
     * @return a List of all the messages posted by a certain account id
     */
    public List<Message> findAllByPostedBy(Integer postedBy);
}
