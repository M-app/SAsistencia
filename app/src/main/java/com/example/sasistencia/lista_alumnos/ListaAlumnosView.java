package com.example.sasistencia.lista_alumnos;

import com.example.sasistencia.domain.Alumno;

import java.util.ArrayList;

public interface ListaAlumnosView {
    void abrirDetalleAlumno(String idGrado, String clave);
    void mostrarAlumnos(ArrayList<Alumno> listaAlumnos);
    void notificarAlumnoEliminado();
}
