package com.example.interfaces_integradora.Retrofit;

public class PostUserChangePassword {
    String password;
    String password_confirmation;

    public PostUserChangePassword(String password, String password_confirmation) {
        this.password = password;
        this.password_confirmation = password_confirmation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }
}
