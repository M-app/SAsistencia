package com.example.sasistencia.detalle_alumno;

import com.example.sasistencia.detalle_alumno.events.DetalleAlumnoEvent;
import com.example.sasistencia.domain.Alumno;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DetalleAlumnoPresenterImpl implements DetalleAlumnoPresenter {

    DetalleAlumnoView view;
    DetalleAlumnoInteractor interactor;

    public DetalleAlumnoPresenterImpl(DetalleAlumnoView view){
       this.view = view;
       interactor = new DetalleAlumnoInteractorImpl();
    }

    @Override
    public void setGrado(String grado) {
        interactor.setGrado(grado);
    }

    @Override
    public void mostrarDatosAlumnos(String claveAlumno) {
        interactor.mostrarDatosAlumnos(claveAlumno);
    }

    @Override
    public void eliminarFalta(Alumno alumno, String fecha) {
        interactor.eliminarFalta(alumno,fecha);
    }

    @Override
    public void agregarAlumno(Alumno alumno) {
        interactor.agregarAlumno(alumno);
    }

    @Override
    public void modificarAlumno(Alumno alumno) {
        interactor.modificarAlumno(alumno);
    }

    @Override
    public void enableTxtNombre() {

    }

    @Override
    public void disableTxtNombre() {

    }

    @Override
    public void enableTxtApellidos() {

    }

    @Override
    public void disableTxtApellidos() {

    }

    @Override
    public void enableTxtTelefono() {

    }

    @Override
    public void disableTxtTelefono() {

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
    public void getCantidadAlumnos(String idGrado) {
        interactor.getCantidadAlumnos(idGrado);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DetalleAlumnoEvent event){
        switch (event.getTipoEvento()){
            case DetalleAlumnoEvent.ALUMNO_OBTENIDO:
                view.mostrarDatos((Alumno) event.getMensaje());
                break;
            case DetalleAlumnoEvent.NUMERO_ALUMNOS:
                view.setClaveAlumnoNuevo(Integer.toString((Integer) event.getMensaje()+1));
                break;
            case DetalleAlumnoEvent.ALUMNO_MODIFICADO:
                view.mostrarMensaje("Alumno modificado con éxito");
                view.abrirFragmentListaAlumnos();
            case DetalleAlumnoEvent.ALUMNO_AGREGADO:
                view.mostrarMensaje("Alumno agregado");
                view.abrirFragmentListaAlumnos();
            default:
                break;
        }
    }
}
