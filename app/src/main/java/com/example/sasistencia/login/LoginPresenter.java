package com.example.sasistencia.login;

import com.example.sasistencia.login.events.LoginEvent;

public interface LoginPresenter {
    void onCreate();
    void onResumen();
    void onPause();
    void onDestroy();
    void validarLogin(String email, String password);
    void errorLogin(String error);
    void exitoLogin(String rolMaestro);
    void onEventMainThread(LoginEvent event);
    void checkLogin();
}
