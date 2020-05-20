package com.example.sasistencia.detalle_alumno.events;

public class DetalleAlumnoEvent {
    public static final int ALUMNO_OBTENIDO = 0;
    public static final int FALTA_ELIMINADA = 1;
    public static final int ALUMNO_AGREGADO = 2;
    public static final int ALUMNO_MODIFICADO = 3;
    public static final int NUMERO_ALUMNOS = 4;


    private int tipoEvento;
    private Object mensaje;

    public int getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(int tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Object getMensaje() {
        return mensaje;
    }

    public void setMensaje(Object mensaje) {
        this.mensaje = mensaje;
    }
}
