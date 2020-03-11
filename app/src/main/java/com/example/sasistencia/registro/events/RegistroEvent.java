package com.example.sasistencia.registro.events;

public class RegistroEvent {

    public static final int EXITO_REGISTRO = 0;
    public static final int ERROR_REGISTRO = 1;

    private int tipoEvento;
    private String mensaje;

    public int getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(int tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
