package com.example.sasistencia;

import java.util.List;

public class Alumno {
    private Integer clave;
    private String apellidos;
    private String nombres;
    private Integer telefono;
    private List<String> inasistencias;

    public Alumno() {
    }

    public Alumno(Integer clave, String apellidos, String nombres, Integer telefono, List<String> inasistencias) {
        this.clave = clave;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.telefono = telefono;
        this.inasistencias = inasistencias;
    }

    public Integer getClave() {
        return clave;
    }

    public void setClave(Integer clave) {
        this.clave = clave;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public List<String> getInasistencias() {
        return inasistencias;
    }

    public void setInasistencias(List<String> inasistencias) {
        this.inasistencias = inasistencias;
    }
}
