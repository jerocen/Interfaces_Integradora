package com.example.interfaces_integradora.Retrofit;

public class PostUserRegister {

    private String name;
    private String email;
    private String password;

    public PostUserRegister(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getter y Setter para el nombre
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter y Setter para el email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter y Setter para la contraseña
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
