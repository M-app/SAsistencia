package com.example.sasistencia.lista_alumnos;

public interface ListaAlumnosRepository {
    void mostrarAlumnos(String nombreGrado);
    void eliminarAlumno(String nombreGrado, String claveAlumno);
}
