package com.example.sasistencia.registro;

public interface RegistroPresenter {

    boolean validarInputs(String nombre, String email, String pass);

    void manejarRegistro(String email, String nombre ,String pass);

    void errorRegistro(String mensaje);

    void exitoRegistro();

    void onResume();

    void onStop();

}
