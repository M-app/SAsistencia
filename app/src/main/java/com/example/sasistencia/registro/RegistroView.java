package com.example.sasistencia.registro;

public interface RegistroView {
    void habilitarInputs();
    void desabilitarInputs();
    void mostrarError(String error);
    void exitoRegistro();
    void iniciarSesion();
    void mostrarProgress();
    void ocultarProgress();
}
