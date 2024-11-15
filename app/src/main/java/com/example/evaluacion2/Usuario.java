package com.example.evaluacion2;

public class Usuario {
    private String nombreUsuario;
    private String nombreReal;
    private String contrasena;
    private String email;

    // Constructor vacío requerido por Firebase
    public Usuario() {
    }

    // Constructor completo
    public Usuario(String nombreUsuario, String nombreReal, String contrasena, String email) {
        this.nombreUsuario = nombreUsuario;
        this.nombreReal = nombreReal;
        this.contrasena = contrasena;
        this.email = email;
    }

    // Getters y Setters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
