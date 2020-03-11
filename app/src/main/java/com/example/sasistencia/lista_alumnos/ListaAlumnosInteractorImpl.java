package com.example.sasistencia.lista_alumnos;

public class ListaAlumnosInteractorImpl implements ListaAlumnosInteractor {

    ListaAlumnosRepository repository;

    public ListaAlumnosInteractorImpl() {
        repository = new ListaAlumnosRepositoryImpl();
    }

    @Override
    public void eliminarAlumno(String nombreGrado, String claveAlumno) {
        repository.eliminarAlumno(nombreGrado,claveAlumno);
    }

    @Override
    public void mostrarAlumnos(String nombreGrado) {
        repository.mostrarAlumnos(nombreGrado);
    }
}
