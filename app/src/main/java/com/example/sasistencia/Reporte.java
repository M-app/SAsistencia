package com.example.sasistencia;

public class Reporte {
    String nombreGrado;
    boolean asistenciaTomada;
    String profesor;

    public Reporte() {
    }

    public Reporte(String nombreGrado,boolean asistenciaTomada, String profesor) {
        this.nombreGrado = nombreGrado;
        this.asistenciaTomada = asistenciaTomada;
        this.profesor = profesor;
    }

    public void setNombreGrado(String nombreGrado){
        this.nombreGrado = nombreGrado;
    }

    public String getNombreGrado(){
        return this.nombreGrado;
    }

    public boolean isAsistenciaTomada() {
        return asistenciaTomada;
    }

    public void setAsistenciaTomada(boolean asistenciaTomada) {
        this.asistenciaTomada = asistenciaTomada;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
}
