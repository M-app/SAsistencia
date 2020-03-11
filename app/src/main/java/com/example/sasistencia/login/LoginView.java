package com.example.sasistencia.login;

public interface LoginView {
    void removerTitleBar();
    void habilitarInputs();
    void desabilitarInputs();
    void mostrarProgressBar();
    void ocultarProgressBar();
    void manejarLogin();
    void abrirActivityRegistro();
    void mostrarError(String error);
    void abrirActivityDocente();
    void abrirActivityAdmin();
}
