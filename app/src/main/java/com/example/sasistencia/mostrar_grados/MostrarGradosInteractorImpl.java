package com.example.sasistencia.mostrar_grados;

public class MostrarGradosInteractorImpl implements MostrarGradosInteractor {

    MostrarGradosRepository repository;

    public MostrarGradosInteractorImpl(){
        repository = new MostrarGradosRepositoryImpl();
    }

    @Override
    public void mostrarGrados() {
        repository.mostrarGrados();
    }
}
