package com.example.myapplication;


import java.io.Serializable;

public class PerFil implements Serializable {
    private String nombre;
    private String relacion;
    private String email;

    public PerFil(String nombre, String relacion, String email) {
        this.nombre = nombre;
        this.relacion = relacion;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRelacion() {
        return relacion;
    }

    public String getEmail() {
        return email;
    }


}