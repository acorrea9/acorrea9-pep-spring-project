package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;

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
        // return messageRepository.getAllMessages();
        return null;
    }

    /**
     * Uses the MessageRepository to retrieve a message by its ID from the database.
     * @param id the message_id.
     * @return a message with the specific message_id if successful, null if not successfully found
     */
    public Message getMessageById(int id) {
        // return messageRepository.getMessageById(id);
        return null;
    }

    /**
     * Uses the MessageRepository to delete a message by its ID from the database.
     * @param id the message_id.
     * @return the deleted message if successful, null if not successfully deleted
     */
    public Message deleteMessage(int id) {
        // Message message = getMessageById(id);
        // if(message != null) {
        //     if(messageRepository.deleteMessage(id)) {
        //         return message;
        //     }
        // }
        return null;
    }

    /**
     * Uses the MessageRepository to update a message by its ID from the database.
     * @param id the message_id.
     * @return the new updated message if successful, null if not successfully updated
     */
    public Message updateMessage(int id, Message message) {
        // if((getMessageById(id) != null) && (message.getMessageText().length() > 0) && (message.getMessageText().length() <= 255)) {
        //     if(messageRepository.updateMessage(id, message)) {
        //         return getMessageById(id);
        //     }
        // }
        return null;
    }

    /**
     * Uses the MessageRepository to retrieve a list of all messages written by a particular user.
     * @param id the account_id.
     * @return all messages of a particular user
     */
    public List<Message> getAllMessagesByAccountId(int id) {
        // return messageRepository.getAllMessagesByAccountId(id);
        return null;
    }
}
