package com.example.sasistencia.detalle_alumno;

import com.example.sasistencia.domain.Alumno;

public class DetalleAlumnoInteractorImpl implements DetalleAlumnoInteractor {

    DetalleAlumnoRepository repository;

    public DetalleAlumnoInteractorImpl(){
        repository = new DetalleAlumnoRepositoryImpl();
    }

    @Override
    public void getCantidadAlumnos(String idGrado) {
        repository.getCantidadAlumnos(idGrado);
    }

    @Override
    public void setGrado(String grado) {
        repository.setGrado(grado);
    }

    @Override
    public void mostrarDatosAlumnos(String claveAlumno) {
        repository.mostrarDatosAlumno(claveAlumno);
    }

    @Override
    public void eliminarFalta(Alumno alumno, String fecha) {
        repository.eliminarFalta(alumno,fecha);
    }

    @Override
    public void agregarAlumno(Alumno alumno) {
        repository.agregarAlumno(alumno);
    }

    @Override
    public void modificarAlumno(Alumno alumno) {
        repository.modificarAlumno(alumno);
    }
}
