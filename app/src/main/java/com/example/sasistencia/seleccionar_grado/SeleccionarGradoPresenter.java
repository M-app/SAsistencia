package com.example.sasistencia.seleccionar_grado;

public interface SeleccionarGradoPresenter {
    void obtenerNombreMaestro();
    void cerrarSesion();
    void cargarListaAlumnos();
    void onResume();
    void onStop();
}
