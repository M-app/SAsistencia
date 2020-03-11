package com.example.sasistencia.tomar_asistencia;

import com.example.sasistencia.domain.Maestro;
import com.example.sasistencia.Reporte;
import com.example.sasistencia.domain.Alumno;
import com.example.sasistencia.tomar_asistencia.events.TomarAsistenciaEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class TomarAsistenciaPresenterImpl implements TomarAsistenciaPresenter {

    TomarAsistenciaView view;
    TomarAsistenciaInteractor interactor;
    private String GRADO = "";

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    public TomarAsistenciaPresenterImpl(TomarAsistenciaView view){
        this.view = view;
        interactor = new TomarAsistenciaInteractorImpl();
    }

    @Override
    public void getListaAlumnos(String nombreGrado) {
        interactor.getListaAlumnos(nombreGrado);
        GRADO = nombreGrado;
    }

    @Override
    public void getMaestroActual() {
        interactor.getMaestroActual();
    }

    @Override
    public void cambiarEstatusAsistencia(Reporte reporte) {
        interactor.cambiarEstatusAsistencia(reporte);
    }

    @Override
    public void modificarAsistenciaAlumno(String nombreGrado, Alumno alumno) {
        interactor.modificarAsistenciaAlumno(nombreGrado,alumno);
    }

    @Override
    public void ingresarReporte(Reporte reporte) {
        interactor.ingresarReporte(reporte);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TomarAsistenciaEvent event){
        switch (event.getTipoEvento()){
            case TomarAsistenciaEvent.LISTA_ALUMNOS_OBTENIDA:
                view.llenarListaAlumnos((ArrayList) event.getMensaje());
                view.setListaAlumnos((ArrayList) event.getMensaje());
                break;
            case TomarAsistenciaEvent.MAESTRO_OBTENIDO:
                interactor.ingresarReporte(new Reporte(GRADO,
                        false,
                        ((Maestro)event.getMensaje()).getNombre()));
                break;
            case TomarAsistenciaEvent.REF_REPORTE_CREADA:

                break;
            case TomarAsistenciaEvent.ASISTENCIA_ALUMNO_MODIFICADA:
                interactor.getListaAlumnos(GRADO);
                break;
            case TomarAsistenciaEvent.ASISTENCIA_TOMADA:

                break;
            default:
                break;
        }
    }
}
