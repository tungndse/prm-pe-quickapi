package com.example.prmpemobile.model;


import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private String status;
    private String message;
    @SerializedName("account")
    private AccountGetDto account; // Ensure this matches the server response

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AccountGetDto getAccount() {
        return account;
    }

    public void setAccount(AccountGetDto account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", account=" + account +
                '}';
    }
}