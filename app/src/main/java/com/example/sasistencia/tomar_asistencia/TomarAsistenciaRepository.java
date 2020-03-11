package com.example.sasistencia.tomar_asistencia;

import com.example.sasistencia.Reporte;
import com.example.sasistencia.domain.Alumno;

public interface TomarAsistenciaRepository {
    void getListaAlumnos(String nombreGrado);
    void getMaestroActual();
    void cambiarStatusAsistencia(Reporte reporte);
    void modificarAsistenciaAlumno(String nombreGrado, Alumno alumno);
    void ingresarReporte(Reporte reporte);
}
