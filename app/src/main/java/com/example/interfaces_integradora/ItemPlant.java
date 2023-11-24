package com.example.interfaces_integradora;

public class ItemPlant
{
    String nombre;
    String usuario;
    int image;

    public ItemPlant(String nombre, String usuario, int image) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.image = image;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
