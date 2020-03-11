package com.example.sasistencia.login.events;

public class LoginEvent {
    public static final int EXITO_LOGIN = 0;
    public static final int ERROR_LOGIN = 1;

    private int tipoEvento;
    private String mensaje;

    public int getTipoEvento(){
        return tipoEvento;
    }

    public void setTipoEvento(int tipoEvento){
        this.tipoEvento = tipoEvento;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
