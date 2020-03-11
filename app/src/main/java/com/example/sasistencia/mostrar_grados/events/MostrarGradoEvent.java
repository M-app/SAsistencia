package com.example.sasistencia.mostrar_grados.events;

public class MostrarGradoEvent {
    public static final int GRADOS_RECUPERADOS = 0;

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
