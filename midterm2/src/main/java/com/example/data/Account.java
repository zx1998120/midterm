package com.example.data;

public class Account {
    private Integer accountId;
    private String login;
    private Integer pinCode;
    private String holderName;
    private Double balance;
    private String status;

    public Account(Integer accountId, String login, Integer pinCode, String holderName, Double balance, String status) {
        this.accountId = accountId;
        this.login = login;
        this.pinCode = pinCode;
        this.holderName = holderName;
        this.balance = balance;
        this.status = status;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
