package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

public class Perfil {
    private String nombre;
    private String relacion;
    private String email;

    public Perfil(String nombre, String relacion, String email) {
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

    public String toJson() {
        try {
            JSONObject json = new JSONObject();
            json.put("nombre", nombre);
            json.put("relacion", relacion);
            json.put("email", email);
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Perfil fromJson(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            String nombre = json.getString("nombre");
            String relacion = json.getString("relacion");
            String email = json.getString("email");
            return new Perfil(nombre, relacion, email);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}