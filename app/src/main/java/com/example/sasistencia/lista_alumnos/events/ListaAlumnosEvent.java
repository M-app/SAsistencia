package com.example.sasistencia.lista_alumnos.events;

public class ListaAlumnosEvent {
    public static final int LISTA_ALUMNOS_OBTENIDA = 0;
    public static final int ALUMNO_ELIMINADO = 1;


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
