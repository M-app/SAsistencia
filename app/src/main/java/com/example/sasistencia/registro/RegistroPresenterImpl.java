package com.example.sasistencia.registro;

import android.text.TextUtils;

import com.example.sasistencia.login.LoginView;
import com.example.sasistencia.registro.events.RegistroEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RegistroPresenterImpl implements RegistroPresenter {

    RegistroView registroView;
    RegistroInteractor registroInteractor;

    public RegistroPresenterImpl(RegistroView registroView){
        this.registroView = registroView;
        registroInteractor = new RegistroInteractorImpl();
    }

    @Override
    public boolean validarInputs(String nombre, String email, String pass) {
        return !TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(pass);
    }

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void manejarRegistro(String email,String nombre,String pass) {
        registroInteractor.manejarRegistro(email, nombre, pass);
    }

    @Override
    public void errorRegistro(String mensaje) {

    }

    @Override
    public void exitoRegistro() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RegistroEvent event){
        switch (event.getTipoEvento()){
            case RegistroEvent.EXITO_REGISTRO:
                registroView.exitoRegistro();
                break;
            case RegistroEvent.ERROR_REGISTRO:
                registroView.mostrarError(event.getMensaje());
                break;
            default:
                break;
        }
    }
}
