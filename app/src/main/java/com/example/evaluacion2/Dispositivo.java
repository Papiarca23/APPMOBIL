package com.example.evaluacion2;

public class Dispositivo {
    private String nombre;
    private String clave;
    private String estado;

    // Constructor vacío requerido para Firebase
    public Dispositivo() {}

    public Dispositivo(String nombre, String clave, String estado) {
        this.nombre = nombre;
        this.clave = clave;
        this.estado = estado;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}