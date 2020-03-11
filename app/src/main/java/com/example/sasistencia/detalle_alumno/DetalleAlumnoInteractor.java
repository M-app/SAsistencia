package com.example.sasistencia.detalle_alumno;

import com.example.sasistencia.domain.Alumno;

public interface DetalleAlumnoInteractor {
    void setGrado(String grado);
    void mostrarDatosAlumnos(String claveAlumno);
    void eliminarFalta(Alumno alumno, String fecha);
    void agregarAlumno(Alumno alumno);
    void modificarAlumno(Alumno alumno);
    void getCantidadAlumnos(String idGrado);
}
