package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    /**
     * Uses constructor injection to inject the MessageRepository dependency bean 
     * into the MessageService bean
     */
    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Uses the MessageRepository to insert a message to the database.
     * @param message a message object.
     * @return a message with a message_id if it was successfully inserted, null if it was not successfully inserted
     */
    public Message createMessage(Message message) {
        if((message.getMessageText().length() > 0) && (message.getMessageText().length() <= 255)) {
            return messageRepository.save(message);
        }
        return null;
    }

    /**
     * Uses the MessageRepository to retrieve a list of all messages.
     * @return all messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Uses the MessageRepository to retrieve a message by its ID from the database.
     * @param id the message_id.
     * @return a message with the specific message_id if successful, null if not successfully found
     */
    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    /**
     * Uses the MessageRepository to delete a message by its ID from the database.
     * @param id the message_id.
     * @return the number of deleted messages, only one message should be deleted
     */
    public long deleteMessage(int id) {
        long prevCount = messageRepository.count();
        if(messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return prevCount - messageRepository.count();
        }
        return 0;
    }

    /**
     * Uses the MessageRepository to update a message by its ID from the database.
     * @param id the message_id.
     * @return the number of updated messages, only one message should be updated
     */
    public int updateMessage(int id, Message message) {
        if(messageRepository.existsById(id) && (message.getMessageText().length() > 0) && (message.getMessageText().length() <= 255)) {
            Message temp = messageRepository.findById(id).get();
            temp.setMessageText(message.getMessageText());
            messageRepository.save(temp);
            return 1;
        }
        return 0;
    }

    /**
     * Uses the MessageRepository to retrieve a list of all messages written by a particular user.
     * @param id the account_id.
     * @return all messages of a particular user
     */
    public List<Message> getAllMessagesByAccountId(int id) {
        return messageRepository.findAllByPostedBy(id);
    }
}
