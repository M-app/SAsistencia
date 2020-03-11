package com.example.sasistencia.detalle_alumno;

import com.example.sasistencia.domain.Alumno;

public interface DetalleAlumnoView {
    void mostrarDatos(Alumno alumno);
    void setClaveAlumnoNuevo(String clave);
    void abrirFragmentListaAlumnos();
    void mostrarMensaje(String mensaje);
}
