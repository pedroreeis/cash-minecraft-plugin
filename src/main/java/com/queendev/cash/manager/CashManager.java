package com.queendev.cash.manager;

import com.queendev.cash.models.Account;

import java.util.HashMap;

public class CashManager {

    public HashMap<String, Account> accounts;

    public CashManager() {
        this.accounts = new HashMap<>();
    }

    public Account getAccountOrCreate(String playerName) {
        if(this.accounts.containsKey(playerName.toLowerCase())) {
            return this.accounts.get(playerName.toLowerCase());
        }

        Account account = new Account(playerName, 0.0);
        this.accounts.put(playerName.toLowerCase(), account);
        return account;
    }

    public Account getAccountOrCreate(String playerName, double cash) {
        if(this.accounts.containsKey(playerName.toLowerCase())) {
            return this.accounts.get(playerName.toLowerCase());
        }

        Account account = new Account(playerName, cash);
        this.accounts.put(playerName.toLowerCase(), account);
        return account;
    }
}
