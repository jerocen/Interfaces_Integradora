package com.example.interfaces_integradora.Models;

public class PostUserForgetPassword {
    private String email;

    public PostUserForgetPassword(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
