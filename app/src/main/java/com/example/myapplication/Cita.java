package com.example.myapplication;

public class Cita {
    private String titulo;
    private String medico;
    private String fecha;
    private String hora;

    public Cita(String titulo, String medico, String fecha, String hora) {
        this.titulo = titulo;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMedico() {
        return medico;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    @Override
    public String toString() {
        return "titulo: " + titulo + "\nmedico: " + medico + "\nFecha: " + fecha + "\nHora: " + hora;
    }
}
