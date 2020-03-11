package com.example.sasistencia.lista_alumnos;

import com.example.sasistencia.domain.Alumno;
import com.example.sasistencia.lista_alumnos.events.ListaAlumnosEvent;
import com.example.sasistencia.mostrar_grados.events.MostrarGradoEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class ListaAlumnosPresenterImpl implements ListaAlumnosPresenter{

    ListaAlumnosInteractor interactor;
    ListaAlumnosView view;

    public ListaAlumnosPresenterImpl(ListaAlumnosView view) {
        this.view = view;
        interactor = new ListaAlumnosInteractorImpl();
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
    public void onDestroy() {
        view = null;
    }

    @Override
    public void mostrarAlumnos(String nombreGrado) {
        interactor.mostrarAlumnos(nombreGrado);
    }

    @Override
    public void abrirDetalleAlumno(String idGrado, String clave) {

    }

    @Override
    public void eliminarAlumno(String nombreGrado, String claveAlumno) {
        interactor.eliminarAlumno(nombreGrado,claveAlumno);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ListaAlumnosEvent event){
        switch (event.getTipoEvento()){
            case ListaAlumnosEvent.LISTA_ALUMNOS_OBTENIDA:
                view.mostrarAlumnos((ArrayList<Alumno>) event.getMensaje());
                break;
            case ListaAlumnosEvent.ALUMNO_ELIMINADO:
                view.notificarAlumnoEliminado();
                break;
            default:
                break;
        }
    }
}
