package com.example.sasistencia.tomar_asistencia;

import com.example.sasistencia.Reporte;
import com.example.sasistencia.domain.Alumno;

public class TomarAsistenciaInteractorImpl implements TomarAsistenciaInteractor {

    TomarAsistenciaRepository repository;
    public TomarAsistenciaInteractorImpl(){
        repository = new TomarAsistenciaRepositoryImpl();
    }

    @Override
    public void getListaAlumnos(String nombreGrado) {
        repository.getListaAlumnos(nombreGrado);
    }

    @Override
    public void getMaestroActual() {
        repository.getMaestroActual();
    }

    @Override
    public void cambiarEstatusAsistencia(Reporte reporte) {
        repository.cambiarStatusAsistencia(reporte);
    }

    @Override
    public void modificarAsistenciaAlumno(String nombreGrado, Alumno alumno) {
        repository.modificarAsistenciaAlumno(nombreGrado,alumno);
    }

    @Override
    public void ingresarReporte(Reporte reporte) {
        repository.ingresarReporte(reporte);
    }
}
