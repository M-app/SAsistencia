package com.example.sasistencia.mensajes;

public class MensajesInteractorImpl implements MensajesInteractor {


    MensajesRepository repository;

    public MensajesInteractorImpl(){
        repository = new MensajesRepositoryImpl();
    }

    @Override
    public void getGrados() {
        repository.getGrados();
    }

    @Override
    public void getAlumnosPrimeroBasico() {
        repository.getAlumnosPrimeroBasico();
    }

    @Override
    public void getAlumnosSegundoBasico() {
        repository.getAlumnosSegundoBasico();
    }

    @Override
    public void getAlumnosTerceroBasico() {
        repository.getAlumnosTerceroBasico();
    }

    @Override
    public void getAlumnosCuartoBiologicas() {
        repository.getAlumnosCuartoBiologicas();
    }

    @Override
    public void getAlumnosCuartoCCyLL() {
        repository.getAlumnosCuartoCCyLL();
    }

    @Override
    public void getAlumnosCuartDisenio() {
        repository.getAlumnosCuartDisenio();
    }

    @Override
    public void getAlumnosCuartoCompu() {
        repository.getAlumnosCuartoCompu();
    }

    @Override
    public void getAlumnosQuintoCCyLL() {
        repository.getAlumnosQuintoCCyLL();
    }

    @Override
    public void getAlumnosQuintoBiologicas() {
        repository.getAlumnosQuintoBiologicas();
    }

    @Override
    public void getAlumnosQuintoCompu() {
        repository.getAlumnosQuintoCompu();
    }

    @Override
    public void getAlumnosCuartoMagis() {
        repository.getAlumnosCuartoMagis();
    }

    @Override
    public void getAlumnosQuintoMagis() {
        repository.getAlumnosQuintoMagis();
    }

    @Override
    public void getAlumnosSextoMagis() {
        repository.getAlumnosSextoMagis();
    }
}
