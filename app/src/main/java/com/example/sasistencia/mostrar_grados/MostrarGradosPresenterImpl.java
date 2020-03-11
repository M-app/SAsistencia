package com.example.sasistencia.mostrar_grados;

import com.example.sasistencia.mostrar_grados.events.MostrarGradoEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MostrarGradosPresenterImpl implements MostrarGradosPresenter {

    MostrarGradosInteractor interactor;
    MostrarGradosView view;

    public MostrarGradosPresenterImpl(MostrarGradosView view){
        this.view = view;
        interactor = new MostrarGradosInteractorImpl();
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void mostrarGrados() {
        interactor.mostrarGrados();
    }

    @Override
    public void mostrarListaGrados(ArrayList<String> listaGrados) {
        view.mostrarListaGrados(listaGrados);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MostrarGradoEvent event){
        switch (event.getTipoEvento()){
            case MostrarGradoEvent.GRADOS_RECUPERADOS:
                view.mostrarListaGrados((ArrayList<String>) event.getMensaje());
                break;
            default:
                break;
        }
    }
}
