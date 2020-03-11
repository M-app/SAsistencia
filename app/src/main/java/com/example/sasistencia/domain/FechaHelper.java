package com.example.sasistencia.domain;

import java.text.DateFormat;
import java.util.Calendar;

public class FechaHelper {

    public static int FECHA_CORTA = 0;
    public static int FECHA_LARGA = 1;

    public static String fechaGuardadaCorta = "";
    public static String fechaGuardadaLarga = "";

    static Calendar calendar = Calendar.getInstance();

    public static String getFechaActual(int formato){
        if (formato == FECHA_LARGA)
            return DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        else if (formato == FECHA_CORTA){
            return DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        }else{
            return DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        }
    }

    public static void setFechaGuardadaCorta(String fecha){
        fechaGuardadaCorta = fecha;
    }

    public static String getFechaGuardadaCorta(){
        return fechaGuardadaCorta;
    }

    public static String getFechaGuardadaLarga() {
        return fechaGuardadaLarga;
    }

    public static void setFechaGuardadaLarga(String fechaGuardadaLarga) {
        FechaHelper.fechaGuardadaLarga = fechaGuardadaLarga;
    }
}
