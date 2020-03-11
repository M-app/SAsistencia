package com.example.sasistencia.lista_alumnos;

public interface ListaAlumnosInteractor {
    void mostrarAlumnos(String nombreGrado);
    void eliminarAlumno(String nombreGrado, String claveAlumno);
}
