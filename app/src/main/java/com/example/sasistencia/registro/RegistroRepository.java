package com.example.sasistencia.registro;

public interface RegistroRepository {
    void manejarRegistro(final String email, final String nombre, final String pass);
}
