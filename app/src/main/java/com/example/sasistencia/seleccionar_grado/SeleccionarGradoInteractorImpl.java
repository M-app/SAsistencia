package com.example.sasistencia.seleccionar_grado;

public class SeleccionarGradoInteractorImpl implements SeleccionarGradoInteractor {

    SeleccionarGradoRepository repository;

    public SeleccionarGradoInteractorImpl(){
        repository = new SeleccionarGradoRepositoryImpl();
    }

    @Override
    public void cerrarSesion() {
        repository.cerrarSesion();
    }

    @Override
    public void obtenerNombreMaestro() {
        repository.obtenerNombresMaestro();
    }
}
