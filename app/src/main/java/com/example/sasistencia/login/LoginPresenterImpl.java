package com.example.sasistencia.login;

import com.example.sasistencia.login.events.LoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginPresenterImpl implements LoginPresenter {

    LoginInteractor loginInteractor;
    LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginInteractor = new LoginInteractorImpl();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResumen() {
        EventBus.getDefault().register(this);

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void errorLogin(String error) {
        loginView.ocultarProgressBar();
        loginView.habilitarInputs();
        loginView.mostrarError(error);
    }

    @Override
    public void exitoLogin(String rolMaestro) {
        if (rolMaestro.equals("administrador")) {
            loginView.ocultarProgressBar();
            loginView.habilitarInputs();
            loginView.abrirActivityAdmin();
        }else if(rolMaestro.equals("docente")){
            loginView.ocultarProgressBar();
            loginView.habilitarInputs();
            loginView.abrirActivityDocente();
        }
    }

    @Override
    public void validarLogin(String email, String password) {
        loginView.desabilitarInputs();
        loginView.mostrarProgressBar();
        loginInteractor.loginUsuario(email,password);
    }

    @Override
    public void checkLogin() {
        loginInteractor.checkLogin();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEvent event) {
        switch (event.getTipoEvento()){
            case LoginEvent.EXITO_LOGIN:
                exitoLogin(event.getMensaje());
                break;
            case LoginEvent.ERROR_LOGIN:
                loginView.mostrarError(event.getMensaje());
                break;
            default:
                break;
        }
    }
}
