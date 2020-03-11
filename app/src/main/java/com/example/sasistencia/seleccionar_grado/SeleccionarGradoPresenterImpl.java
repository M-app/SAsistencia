package com.example.sasistencia.seleccionar_grado;

import com.example.sasistencia.seleccionar_grado.events.SeleccionarGradoEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SeleccionarGradoPresenterImpl implements SeleccionarGradoPresenter {

    SeleccionarGradoInteractor interactor;
    SeleccionarGradoView view;
    public SeleccionarGradoPresenterImpl(SeleccionarGradoView view){
        interactor = new SeleccionarGradoInteractorImpl();
        this.view = view;
    }

    @Override
    public void obtenerNombreMaestro() {
        interactor.obtenerNombreMaestro();
    }

    @Override
    public void cerrarSesion() {
        interactor.cerrarSesion();
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
    public void cargarListaAlumnos() {
        view.abrirActivityTomarAsistencia();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SeleccionarGradoEvent event){
        switch (event.getTipoEvento()){
            case SeleccionarGradoEvent.NOMBRE_MAESTRO_ENCONTRADO:
                view.mostrarNombre(event.getMensaje());
                view.enableControls();
                break;
            case SeleccionarGradoEvent.SESION_CERRADA:
                view.abrirActivityLogin();
                break;
            default:
                break;
        }
    }
}
