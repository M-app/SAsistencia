package com.example.sasistencia.tomar_asistencia;

import com.example.sasistencia.Reporte;
import com.example.sasistencia.domain.Alumno;

public interface TomarAsistenciaPresenter {
    void onStart();
    void onStop();
    void onDestroy();
    void getListaAlumnos(String nombreGrado);
    void getMaestroActual();
    void cambiarEstatusAsistencia(Reporte reporte);
    void modificarAsistenciaAlumno(String nombreGrado, Alumno alumno);
    void ingresarReporte(final Reporte reporte);
}
