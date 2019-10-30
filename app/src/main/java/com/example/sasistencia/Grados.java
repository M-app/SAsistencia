package com.example.sasistencia;

import android.util.Log;

public class Grados {
    private static String PRIMERO_BASICO = "Primero Básico";
    private static String SEGUNDO_BASICO = "Segundo Básico";
    private static String TERCERO_BASICO = "Tercero Básico";
    private static String CUARTO_BACH = "Cuarto Bachillerato";
    private static String QUINTO_BACH = "Quinto Bachillerato";
    private static String CUARTO_MAGIS = "Cuarto Magisterio";
    private static String QUINTO_MAGIS = "Quinto Magisterio";
    private static String SEXTO_MAGIS = "Sexto Magisterio";

    private static String ID_PRIMERO_BASICO = "PrimeroBasico";
    private static String ID_SEGUNDO_BASICO = "SegundoBasico";
    private static String ID_TERCERO_BASICO = "TerceroBasico";
    private static String ID_CUARTO_BACH = "CuartoBachillerato";
    private static String ID_QUINTO_BACH = "QuintoBachillerato";
    private static String ID_CUARTO_MAGIS = "CuartoMagisterio";
    private static String ID_QUINTO_MAGIS = "QuintoMagisterio";
    private static String ID_SEXTO_MAGIS = "SextoMagisterio";

    public static String getIdByGrado(String nombreGrado){
        String grado = "";

        switch (nombreGrado){
            case "Primero Básico":
                grado = ID_PRIMERO_BASICO;
                break;
            case "Segundo Básico":
                grado = ID_SEGUNDO_BASICO;
                break;
            case "Tercero Básico":
                grado = ID_TERCERO_BASICO;
                break;
            case "Cuarto Bachillerato":
                grado = ID_CUARTO_BACH;
                break;
            case "Quinto Bachillerato":
                grado = ID_QUINTO_BACH;
                break;
            case "Cuarto Magisterio":
                grado = ID_CUARTO_MAGIS;
                break;
            case "Quinto Magisterio":
                grado = ID_QUINTO_MAGIS;
                break;
            case "Sexto Magisterio":
                grado = ID_SEXTO_MAGIS;
                break;
            default:
                grado = "DESCONOCIDO";
                break;
        }
        return grado;
    }

    public static String getGradoById(String idGrado){
        String grado = "";
        Log.v("ID DEL GRADO PERROS",idGrado);
        switch (idGrado){
            case "PrimeroBasico":
                grado = PRIMERO_BASICO;
                break;
            case "SegundoBasico":
                grado = SEGUNDO_BASICO;
                break;
            case "TerceroBasico":
                grado = TERCERO_BASICO;
                break;
            case "CuartoBachillerato":
                grado = CUARTO_BACH;
                break;
            case "QuintoBachillerato":
                grado = QUINTO_BACH;
                break;
            case "CuartoMagisterio":
                grado = CUARTO_MAGIS;
                break;
            case "QuintoMagisterio":
                grado = QUINTO_MAGIS;
                break;
            case "SextoMagisterio":
                grado = SEXTO_MAGIS;
                break;
            default:
                grado = "DESCONOCIDO";
                break;
        }
        return grado;
    }
}
