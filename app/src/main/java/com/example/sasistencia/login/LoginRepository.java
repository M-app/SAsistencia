package com.example.sasistencia.login;

public interface LoginRepository {
    void loginUsuario(String email, String password);
    void checkLogin();
}
