package com.live.concert.service;

import com.live.concert.entity.Account;
import com.live.concert.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long accountId) throws AccountNotFoundException {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

}