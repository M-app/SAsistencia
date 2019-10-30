package com.example.sasistencia;

public class Maestro {
    String email;
    String nombre;
    String estatus;
    String rol;

    public Maestro(){}

    public Maestro(String email, String nombre, String estatus,String rol) {
        this.email = email;
        this.nombre = nombre;
        this.estatus = estatus;
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
