package com.example.sasistencia;

import android.widget.ListView;

import java.util.List;

public class Faltas {
    private String fecha;
    private String maestro;
    private List<String> inasistencias;

    public Faltas() {
    }

    public Faltas(String fecha, String maestro) {
        this.fecha = fecha;
        this.maestro = maestro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMaestro() {
        return maestro;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
    }
}
