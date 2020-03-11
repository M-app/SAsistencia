package com.example.sasistencia.tomar_asistencia;

import com.example.sasistencia.domain.Alumno;

import java.util.List;

public interface TomarAsistenciaView {
    void llenarListaAlumnos(List<Alumno> listaAlumnos);
    void abrirActivitySeleccionarGrado();
    void setListaAlumnos(List<Alumno> listaAlumnos);
}
