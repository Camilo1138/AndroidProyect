package com.example.myapplication;

import java.io.*;
import java.util.ArrayList;

public class Medico implements Serializable {
    private String nombre;
    private String especialidad;
    private String telefono;
    private String email;
    private String direccion;

    // Constructor
    public Medico(String nombre, String especialidad, String telefono, String email, String direccion) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    // Métodos para guardar y cargar médicos desde un archivo
    public static ArrayList<Medico> cargarMedicosDesdeArchivo(String pathArchivo) {
        ArrayList<Medico> listaMedicos = new ArrayList<>();
        File archivo = new File(pathArchivo);

        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                listaMedicos = (ArrayList<Medico>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return listaMedicos;
    }

    public static void guardarMedicosEnArchivo(String pathArchivo, ArrayList<Medico> listaMedicos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathArchivo))) {
            oos.writeObject(listaMedicos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

