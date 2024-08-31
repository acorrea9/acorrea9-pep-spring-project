package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    /**
     * Uses constructor injection to inject the AccountService and MessageService 
     * dependency beans into the SocialMediaController controller/bean
     */
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * Handler to process new User registrations.
     * The @RequestBody annotation will automatically convert the JSON of the POST request into an Account object.
     * AccountService will return:
     *      an account with an id if successful, 
     *      an account without an id if username was taken, 
     *      or null if unsuccessful or did not meet username/password constraints.
     * @param account the account details in the request body that was converted from JSON into an Account object
     * @return ResponseEntity with 200 message and an account with an id if registration was successful,
     *         ResponseEntity with 400 message if registration was unsuccessful or did not meet username/password constraints,
     *         ResponseEntity with 409 message if registration was unsuccessful due to the username being taken.
     */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        Account registeredAccount = accountService.registerAccount(account);
        if(registeredAccount == null) {
            return ResponseEntity.status(400).build();
        }
        else if(registeredAccount.getAccountId() == null) {
            return ResponseEntity.status(409).build();
        }
        else {
            return ResponseEntity.status(200).body(registeredAccount);
        }
    }

    /**
     * Handler to process new User logins.
     * The @RequestBody annotation will automatically convert the JSON of the POST request into an Account object.
     * AccountService will return:
     *      an account with an id if successful,
     *      or null if verifying/logging in an Account was unsuccessful.
     * @param account the account details in the request body that was converted from JSON into an Account object
     * @return ResponseEntity with 200 message and an account with an id if verifying/logging was successful,
     *         ResponseEntity with 401 message if verifying/logging was unsuccessful.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account temp = accountService.loginAccount(account);
        if(temp != null) {
            return ResponseEntity.status(200).body(temp);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * Handler to process the creation of new messages.
     * The @RequestBody annotation will automatically convert the JSON of the POST request into a Message object.
     * MessageService will return: 
     *      a message with an id if successful,
     *      or null if creating a message was unsuccessful.
     * @param message the message details in the request body that was converted from JSON into a Message object
     * @return ResponseEntity with 200 message and a message with an id if creation was successful,
     *         ResponseEntity with 400 message if message creation was unsuccessful.
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if(accountService.existsById(message.getPostedBy())) {
            Message addedMessage = messageService.createMessage(message);
            if(addedMessage != null) {
                return ResponseEntity.status(200).body(addedMessage);
            }
        }
        return ResponseEntity.status(400).build();
    }

    /**
     * Handler to retrieve all messages.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    /**
     * Handler to retrieve a message by its ID.
     * The @PathVariable annotaion will extract the parameter from the URI in order to get the message_id.
     * MessageService will return: 
     *      a message with the same given id,
     *      or null if retrieving a Message was unsuccessful.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin.
     * @return ResponseEntity with 200 message and a message with an id if creation was successful,
     *         ResponseEntity with 200 message if unsuccessful.
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable int messageId) {
        Message messageById = messageService.getMessageById(messageId);
        if(messageById != null) {
            return ResponseEntity.status(200).body(messageById);
        }
        else {
            return ResponseEntity.status(200).build();
        }
    }

    /**
     * Handler to delete a message by its ID.
     * The @PathVariable annotaion will extract the parameter from the URI in order to get the message_id.
     * MessageService will return: 
     *      the number of deleted messages
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin.
     * @return ResponseEntity with 200 message and the number of affected rows if only one row was affected,
     *         ResponseEntity with 200 message if deletion was unsuccessful or multiple rows were affected.
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        long deletedMessageCount = messageService.deleteMessage(messageId);
        if(deletedMessageCount == 1) {
            return ResponseEntity.status(200).body(1);
        }
        else {
            return ResponseEntity.status(200).build();
        }
    }

    /**
     * Handler to update a message by its ID.
     * The @PathVariable annotaion will extract the parameter from the URI in order to get the message_id.
     * The @RequestBody annotation will automatically convert the JSON of the POST request into a Message object.
     * MessageService will return: 
     *      the number of updated messages.
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin.
     * @return ResponseEntity with 200 message and the number of affected rows if only one row was affected,
     *         ResponseEntity with 400 message if the update was unsuccessful or multiple rows were affected.
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId, @RequestBody Message message) {
        int updatedMessageCount = messageService.updateMessage(messageId, message);
        if(updatedMessageCount == 1) {
            return ResponseEntity.status(200).body(1);
        }
        else {
            return ResponseEntity.status(400).build();
        }
    }

    /**
     * Handler to retrieve all messages written by a particular user.
     * The @PathVariable annotaion will extract the parameter from the URI in order to get the account_id.
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessages(@PathVariable int accountId) {
        return ResponseEntity.status(200).body(messageService.getAllMessagesByAccountId(accountId));
    }
}
