package com.example.interfaces_integradora;

public class ItemPlant
{
    String nombre;

    int image;

    public ItemPlant(String nombre, int image) {
        this.nombre = nombre;
        this.image = image;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
