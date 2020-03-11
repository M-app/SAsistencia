package com.example.sasistencia.mensajes;

import com.example.sasistencia.domain.Alumno;

import java.util.List;

public interface MensajesView {
    void llenarListaGrados(List<String> grados);
    void llenarPrimeroBasico(List<Alumno> alumnos);
    void llenarSegundoBasico(List<Alumno> alumnos);
    void llenarTerceroBasico(List<Alumno> alumnos);
    void llenarCuartoBiologicas(List<Alumno> alumnos);
    void llenarCuartoCCyLL(List<Alumno> alumnos);
    void llenarCuartoDisenio(List<Alumno> alumnos);
    void llenarCuartoCompu(List<Alumno> alumnos);
    void llenarQuintoCCyLL(List<Alumno> alumnos);
    void llenarQuintoBiologicas(List<Alumno> alumnos);
    void llenarQuintoCompu(List<Alumno> alumnos);
    void llenarCuartoMagisterio(List<Alumno> alumnos);
    void llenarQuintoMagisterio(List<Alumno> alumnos);
    void llenarSextoMagisterio(List<Alumno> alumnos);
}
