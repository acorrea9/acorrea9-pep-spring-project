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

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

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

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessages(@PathVariable int accountId) {
        return ResponseEntity.status(200).body(messageService.getAllMessagesByAccountId(accountId));
    }
}
