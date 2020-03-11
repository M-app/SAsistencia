package com.example.sasistencia.domain;

public class NombresGrados {
    public static final String NOMBRE_PRIMERO_BASICO = "Primero Básico";
    public static final String ID_PRIMERO_BASICO = "PrimeroBasico";
    public static final String NOMBRE_SEGUNDO_BASICO = "Segundo Básico";
    public static final String ID_SEGUNDO_BASICO = "SegundoBasico";
    public static final String NOMBRE_TERCERO_BASICO = "Tercero Básico";
    public static final String ID_TERCERO_BASICO = "TerceroBasico";
    public static final String NOMBRE_CUARTO_BIOLOGICAS = "Cuarto Biológicas";
    public static final String ID_CUARTO_BIOLOGICAS = "CuartoBiologicas";
    public static final String NOMBRE_CUARTO_BACH_CCyLL = "Cuarto Bach CCyLL";
    public static final String ID_CUARTO_BACH_CC_LL = "CuartoBachCCYLL";
    public static final String NOMBRE_CUARTO_BACH_DISENIO = "Cuarto Bach Diseño";
    public static final String ID_CUARTO_BACH_DISENIO = "CuartoBachDisenio";
    public static final String NOMBRE_CUARTO_BACH_COMPU = "Cuarto Bach Compu";
    public static final String ID_CUARTO_BACH_COMPU = "CuartoBachCompu";
    public static final String NOMBRE_QUINTO_BACH_BIOLOGICAS = "Quinto Bach Biológicas";
    public static final String ID_QUINTO_BACH_BIOLOGICAS = "QuintoBachBiologicas";
    public static final String NOMBRE_QUINTO_BACH_CCyLL = "Quinto Bach CCyLL";
    public static final String ID_QUINTO_BACH_CCyLL = "QuintoBachCCyLL";
    public static final String NOMBRE_QUINTO_BACH_COMPU = "Quinto Bach Compu";
    public static final String ID_QUINTO_BACH_COMPU = "QuintoBachCompu";
    public static final String NOMBRE_CUARTO_MAGISTERIO = "Cuarto Magisterio";
    public static final String ID_CUARTO_MAGISTERIO = "CuartoMagisterio";
    public static final String NOMBRE_QUINTO_MAGISTERIO = "Quinto Magisterio";
    public static final String ID_QUINTO_MAGISTERIO = "QuintoMagisterio";
    public static final String NOMBRE_SEXTO_MAGISTERIO = "Sexto Magisterio";
    public static final String ID_SEXTO_MAGISTERIO = "SextoMagisterio";
    public static final String DESCONOCIDO = "DESCONOCIDO";

    public static final int ID_CARRERAS = 0;
    public static final int ID_CARRERA_BASICO = 1;
    public static final int ID_CARRERA_BACHILLERATO = 2;
    public static final int ID_CARRERA_MAGISTERIO = 3;

    public static String getIdGradoFromSpinners(String spinnerCarrera, String spinnerGrado){
        switch (spinnerCarrera+spinnerGrado){
            case "BásicoPrimero":
                return NombresGrados.ID_PRIMERO_BASICO;
            case "BásicoSegundo":
                return NombresGrados.ID_SEGUNDO_BASICO;
            case "BásicoTercero":
                return NombresGrados.ID_TERCERO_BASICO;
            case "BachilleratoCuarto Biológicas":
                return NombresGrados.ID_CUARTO_BIOLOGICAS;
            case "BachilleratoCuarto CCyLL":
                return NombresGrados.ID_CUARTO_BACH_CC_LL;
            case "BachilleratoCuarto Diseño":
                return NombresGrados.ID_CUARTO_BACH_DISENIO;
            case "BachilleratoCuarto Computación":
                return NombresGrados.ID_CUARTO_BACH_COMPU;
            case "BachilleratoQuinto CCyLL":
                return NombresGrados.ID_QUINTO_BACH_CCyLL;
            case "BachilleratoQuinto Biológicas":
                return NombresGrados.ID_QUINTO_BACH_BIOLOGICAS;
            case "BachilleratoQuinto Compu":
                return NombresGrados.ID_QUINTO_BACH_COMPU;
            case "MagisterioCuarto":
                return NombresGrados.ID_CUARTO_MAGISTERIO;
            case "MagisterioQuinto":
                return NombresGrados.ID_QUINTO_MAGISTERIO;
            case "MagisterioSexto":
                return NombresGrados.ID_SEXTO_MAGISTERIO;
            default:
                return DESCONOCIDO;
        }
    }

    public static String getNombreGradoById(String idGrado){
        switch (idGrado){
            case ID_PRIMERO_BASICO:
                return NombresGrados.NOMBRE_PRIMERO_BASICO;
            case ID_SEGUNDO_BASICO:
                return NombresGrados.NOMBRE_SEGUNDO_BASICO;
            case ID_TERCERO_BASICO:
                return NombresGrados.NOMBRE_TERCERO_BASICO;
            case ID_CUARTO_BIOLOGICAS:
                return NombresGrados.NOMBRE_CUARTO_BIOLOGICAS;
            case ID_CUARTO_BACH_CC_LL:
                return NombresGrados.NOMBRE_CUARTO_BACH_CCyLL;
            case ID_CUARTO_BACH_DISENIO:
                return NombresGrados.NOMBRE_CUARTO_BACH_DISENIO;
            case ID_CUARTO_BACH_COMPU:
                return NombresGrados.NOMBRE_CUARTO_BACH_COMPU;
            case ID_QUINTO_BACH_CCyLL:
                return NombresGrados.NOMBRE_QUINTO_BACH_CCyLL;
            case ID_QUINTO_BACH_BIOLOGICAS:
                return NombresGrados.NOMBRE_QUINTO_BACH_BIOLOGICAS;
            case ID_QUINTO_BACH_COMPU:
                return NombresGrados.NOMBRE_QUINTO_BACH_COMPU;
            case ID_CUARTO_MAGISTERIO:
                return NombresGrados.NOMBRE_CUARTO_MAGISTERIO;
            case ID_QUINTO_MAGISTERIO:
                return NombresGrados.NOMBRE_QUINTO_MAGISTERIO;
            case ID_SEXTO_MAGISTERIO:
                return NombresGrados.NOMBRE_SEXTO_MAGISTERIO;
            default:
                return DESCONOCIDO;
        }
    }

    public static String getIdGradoByNombre(String nombreGrado){
        switch (nombreGrado){
            case NOMBRE_PRIMERO_BASICO:
                return ID_PRIMERO_BASICO;
            case NOMBRE_SEGUNDO_BASICO:
                return ID_SEGUNDO_BASICO;
            case NOMBRE_TERCERO_BASICO:
                return ID_TERCERO_BASICO;
            case NOMBRE_CUARTO_BIOLOGICAS:
                return  ID_CUARTO_BIOLOGICAS;
            case NOMBRE_CUARTO_BACH_CCyLL:
                return ID_CUARTO_BACH_CC_LL;
            case NOMBRE_CUARTO_BACH_DISENIO:
                return ID_CUARTO_BACH_DISENIO;
            case NOMBRE_CUARTO_BACH_COMPU:
                return ID_CUARTO_BACH_COMPU;
            case NOMBRE_QUINTO_BACH_CCyLL:
                return ID_QUINTO_BACH_CCyLL;
            case NOMBRE_QUINTO_BACH_BIOLOGICAS:
                return ID_QUINTO_BACH_BIOLOGICAS;
            case NOMBRE_QUINTO_BACH_COMPU:
                return ID_QUINTO_BACH_COMPU;
            case NOMBRE_CUARTO_MAGISTERIO:
                return ID_CUARTO_MAGISTERIO;
            case NOMBRE_QUINTO_MAGISTERIO:
                return ID_QUINTO_MAGISTERIO;
            case NOMBRE_SEXTO_MAGISTERIO:
                return ID_SEXTO_MAGISTERIO;
            default:
                return DESCONOCIDO;
        }
    }
}
