package com.example.sasistencia.mensajes;

import android.util.Log;

import com.example.sasistencia.domain.Alumno;
import com.example.sasistencia.mensajes.events.MensajesEvent;
import com.example.sasistencia.mostrar_grados.events.MostrarGradoEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MensajesPresenterImpl implements MensajesPresenter {

    MensajesInteractor interactor;
    MensajesView view;

    public MensajesPresenterImpl(MensajesView view){
        this.view = view;
        interactor = new MensajesInteractorImpl();
    }

    @Override
    public void getGrados() {
        interactor.getGrados();
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getAlumnosPrimeroBasico() {
        interactor.getAlumnosPrimeroBasico();
    }

    @Override
    public void getAlumnosSegundoBasico() {
        interactor.getAlumnosSegundoBasico();
    }

    @Override
    public void getAlumnosTerceroBasico() {
        interactor.getAlumnosTerceroBasico();
    }

    @Override
    public void getAlumnosCuartoBiologicas() {
        interactor.getAlumnosCuartoBiologicas();
    }

    @Override
    public void getAlumnosCuartoCCyLL() {
        interactor.getAlumnosCuartoCCyLL();
    }

    @Override
    public void getAlumnosCuartDisenio() {
        interactor.getAlumnosCuartDisenio();
    }

    @Override
    public void getAlumnosCuartoCompu() {
        interactor.getAlumnosCuartoCompu();
    }

    @Override
    public void getAlumnosQuintoCCyLL() {
        interactor.getAlumnosQuintoCCyLL();
    }

    @Override
    public void getAlumnosQuintoBiologicas() {
        interactor.getAlumnosQuintoBiologicas();
    }

    @Override
    public void getAlumnosQuintoCompu() {
        interactor.getAlumnosQuintoCompu();
    }

    @Override
    public void getAlumnosCuartoMagis() {
        interactor.getAlumnosCuartoMagis();
    }

    @Override
    public void getAlumnosQuintoMagis() {
        interactor.getAlumnosQuintoMagis();
    }

    @Override
    public void getAlumnosSextoMagis() {
        interactor.getAlumnosSextoMagis();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MensajesEvent event){
        switch (event.getTipoEvento()){
            case MensajesEvent.GRADOS_OBTENIDOS:
                List<String> grados = (List<String>) event.getMensaje();
                view.llenarListaGrados(grados);
                break;
            case MensajesEvent.PRIMERO_BASICO:
                view.llenarPrimeroBasico((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.SEGUNDO_BASICO:
                view.llenarSegundoBasico((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.TERCERO_BASICO:
                view.llenarTerceroBasico((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.CUARTO_BIOLOGICAS:
                view.llenarCuartoBiologicas((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.CUARTO_CCyLL:
                view.llenarCuartoCCyLL((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.CUARTO_DISENIO:
                view.llenarCuartoDisenio((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.CUARTO_COMPU:
                view.llenarCuartoCompu((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.QUINTO_CCyLL:
                view.llenarQuintoCCyLL((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.QUINTO_BIOLOGICAS:
                view.llenarQuintoBiologicas((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.QUINTO_COMPU:
                view.llenarQuintoCompu((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.CUARTO_MAGIS:
                view.llenarCuartoMagisterio((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.QUINTO_MAGIS:
                view.llenarQuintoMagisterio((List<Alumno>) event.getMensaje());
                break;
            case MensajesEvent.SEXTO_MAGIS:
                view.llenarSextoMagisterio((List<Alumno>) event.getMensaje());
                break;
            default:
                break;
        }
    }
}
