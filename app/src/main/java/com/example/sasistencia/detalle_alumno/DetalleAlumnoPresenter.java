package com.example.sasistencia.detalle_alumno;

import com.example.sasistencia.domain.Alumno;

public interface DetalleAlumnoPresenter {
    void setGrado(String grado);
    void mostrarDatosAlumnos(String claveAlumno);
    void eliminarFalta(Alumno alumno, String fecha);
    void agregarAlumno(Alumno alumno);
    void modificarAlumno(Alumno alumno);
    void enableTxtNombre();
    void disableTxtNombre();
    void enableTxtApellidos();
    void disableTxtApellidos();
    void enableTxtTelefono();
    void disableTxtTelefono();
    void onStart();
    void onStop();
    void onDestroy();
    void getCantidadAlumnos(String idGrado);

}
