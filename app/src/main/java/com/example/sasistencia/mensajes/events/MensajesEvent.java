package com.example.sasistencia.mensajes.events;

public class MensajesEvent {
    public static final int GRADOS_OBTENIDOS = 0;
    public static final int PRIMERO_BASICO = 1;
    public static final int SEGUNDO_BASICO = 2;
    public static final int TERCERO_BASICO = 3;
    public static final int CUARTO_BIOLOGICAS = 4;
    public static final int CUARTO_CCyLL = 5;
    public static final int CUARTO_DISENIO = 6;
    public static final int CUARTO_COMPU = 7;
    public static final int QUINTO_CCyLL = 8;
    public static final int QUINTO_BIOLOGICAS = 9;
    public static final int QUINTO_COMPU = 10;
    public static final int CUARTO_MAGIS = 11;
    public static final int QUINTO_MAGIS = 12;
    public static final int SEXTO_MAGIS = 13;


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
