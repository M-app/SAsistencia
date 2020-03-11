package com.example.sasistencia.detalle_alumno;

import com.example.sasistencia.domain.Alumno;

public interface DetalleAlumnoRepository {
    void setGrado(String grado);
    void mostrarDatosAlumno(String claveAlumno);
    void eliminarFalta(Alumno alumno,String fecha);
    void agregarAlumno(Alumno alumno);
    void modificarAlumno(Alumno alumno);
    void getCantidadAlumnos(String idGrado);
}
