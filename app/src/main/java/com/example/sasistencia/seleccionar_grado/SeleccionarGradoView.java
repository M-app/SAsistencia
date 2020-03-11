package com.example.sasistencia.seleccionar_grado;

public interface SeleccionarGradoView {
    void mostrarNombre(String nombre);
    void disableControls();
    void enableControls();
    void configPrimerSpinner();
    void configSegundoSpinner();
    void setFecha();
    void abrirActivityTomarAsistencia();
    void abrirActivityLogin();
}
