package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Uses the AccountDAO to insert an account to the database.
     * @param account an account object.
     * @return an account with an account_id if it was successfully inserted, null if it was 
     * not successfully inserted (eg if the account prerequisites were not met or username was taken)
     */
    public Account registerAccount(Account account) {
        if((account.getUsername().length() != 0) && (account.getPassword().length() >= 4)) {
            if(!accountRepository.usernameExists(account.getUsername())) {
                return accountRepository.registerAccount(account);
            }
            else {
                return account;
            }
        }
        return null;
    }

    /**
     * Uses the AccountDAO to verify an account exists in the database.
     * @param account an account object.
     * @return an account with an account_id if the account exists, null if the account does not exists
     */
    public Account loginAccount(Account account) {
        return accountRepository.loginAccount(account);
    }
}
