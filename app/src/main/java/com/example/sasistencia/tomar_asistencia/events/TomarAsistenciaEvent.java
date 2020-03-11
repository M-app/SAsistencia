package com.example.sasistencia.tomar_asistencia.events;

public class TomarAsistenciaEvent {

    public static final int LISTA_ALUMNOS_OBTENIDA = 0;
    public static final int MAESTRO_OBTENIDO = 1;
    public static final int ASISTENCIA_ALUMNO_MODIFICADA = 2;
    public static final int REF_REPORTE_CREADA = 3;
    public static final int ASISTENCIA_TOMADA = 4;


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
