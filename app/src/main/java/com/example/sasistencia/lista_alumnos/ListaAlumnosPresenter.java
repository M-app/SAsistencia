package com.example.sasistencia.lista_alumnos;

public interface ListaAlumnosPresenter {
    void mostrarAlumnos(String nombreGrado);
    void abrirDetalleAlumno(String idGrado, String clave);
    void eliminarAlumno(String nombreGrado, String claveAlumno);
    void onStart();
    void onStop();
    void onDestroy();
}
