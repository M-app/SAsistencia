package com.example.sasistencia.seleccionar_grado.events;

public class SeleccionarGradoEvent {

    public static final int NOMBRE_MAESTRO_ENCONTRADO = 0;
    public static final int SESION_CERRADA = 1;

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
